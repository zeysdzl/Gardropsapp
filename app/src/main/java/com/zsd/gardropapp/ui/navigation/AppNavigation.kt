package com.zsd.gardropapp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zsd.gardropapp.data.entity.Product
import com.zsd.gardropapp.data.repository.ProductRepository
import com.zsd.gardropapp.ui.screens.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val products = remember { ProductRepository().getDummyProducts() }
    val favoriteProductIds = remember { mutableStateListOf<Int>() }
    val cartItems = remember { mutableStateListOf<Product>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val onFavoriteClick: (Int) -> Unit = { productId ->
        if (favoriteProductIds.contains(productId)) {
            favoriteProductIds.remove(productId)
        } else {
            favoriteProductIds.add(productId)
        }
    }

    val onAddToCart: (Product) -> Unit = { product ->
        scope.launch {
            val message = if (!cartItems.contains(product)) {
                cartItems.add(product)
                "${product.title} sepete eklendi!"
            } else {
                "${product.title} zaten sepetinizde."
            }
            val job = launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite
                )
            }
            delay(500L)
            job.cancel()
        }
    }

    val onRemoveFromCart: (Product) -> Unit = { product ->
        cartItems.remove(product)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                MainScreen(
                    navController = navController,
                    products = products,
                    favoriteProductIds = favoriteProductIds,
                    onFavoriteClick = onFavoriteClick,
                    cartItemCount = cartItems.size
                )
            }
            composable("productDetail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                val product = products.find { it.id == productId }
                if (product != null) {
                    ProductDetailScreen(
                        product = product,
                        navController = navController,
                        onAddToCart = onAddToCart
                    )
                } else {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text("Product not found.", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            composable("favorites") {
                val favoriteProducts = products.filter { it.id in favoriteProductIds }
                FavoritesScreen(
                    navController = navController,
                    favoriteProducts = favoriteProducts,
                    favoriteProductIds = favoriteProductIds,
                    onFavoriteClick = onFavoriteClick,
                    cartItemCount = cartItems.size
                )
            }
            composable("cart") {
                CartScreen(
                    cartItems = cartItems,
                    navController = navController,
                    onRemoveItem = onRemoveFromCart,
                    favoriteItemCount = favoriteProductIds.size
                )
            }
            composable("search") { PlaceholderScreen("Search", navController, favoriteProductIds.size, cartItems.size) }
            composable("add") { PlaceholderScreen("Add", navController, favoriteProductIds.size, cartItems.size) }
            composable("chat") { PlaceholderScreen("Chat", navController, favoriteProductIds.size, cartItems.size) }
            composable("profile") { PlaceholderScreen("Profile", navController, favoriteProductIds.size, cartItems.size) }
        }
    }
}