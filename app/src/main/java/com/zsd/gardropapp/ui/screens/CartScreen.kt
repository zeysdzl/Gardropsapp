package com.zsd.gardropapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zsd.gardropapp.data.entity.Product
import com.zsd.gardropapp.ui.components.CartItem
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartItems: List<Product>,
    navController: NavController,
    onRemoveItem: (Product) -> Unit,
    favoriteItemCount: Int
) {
    val totalPrice = remember(cartItems) {
        cartItems.sumOf {
            it.price.replace("₺", "").trim().toDoubleOrNull() ?: 0.0
        }
    }
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale("tr", "TR")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sepetim", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    BadgedBox(badge = { if (favoriteItemCount > 0) Badge { Text("$favoriteItemCount") } }) {
                        IconButton(onClick = { navController.navigate("favorites") }) {
                            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    BadgedBox(badge = { if (cartItems.isNotEmpty()) Badge { Text("${cartItems.size}") } }) {
                        IconButton(onClick = { /* Zaten sepet ekranında */ }) {
                            Icon(Icons.Outlined.ShoppingCart, contentDescription = "Shopping Bag", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Column(modifier =
                    Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Toplam:", style = MaterialTheme.typography.titleMedium)
                        Text(currencyFormat.format(totalPrice), style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Satın Al", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Sepetiniz boş.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(cartItems) { product ->
                    CartItem(product = product, onRemoveClick = { onRemoveItem(product) })
                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                }
            }
        }
    }
}