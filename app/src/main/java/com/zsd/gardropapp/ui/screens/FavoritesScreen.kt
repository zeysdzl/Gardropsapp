package com.zsd.gardropapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zsd.gardropapp.data.entity.Product
import com.zsd.gardropapp.ui.components.GardropsBottomNavBar
import com.zsd.gardropapp.ui.components.GardropsTopBar
import com.zsd.gardropapp.ui.components.ProductCard
import com.zsd.gardropapp.ui.components.PromotionCard

@Composable
fun FavoritesScreen(
    navController: NavController,
    favoriteProducts: List<Product>,
    favoriteProductIds: List<Int>,
    onFavoriteClick: (Int) -> Unit,
    cartItemCount: Int
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredFavorites = remember(searchQuery, favoriteProducts) {
        if (searchQuery.length >= 2) {
            favoriteProducts.filter {
                it.description.contains(searchQuery, ignoreCase = true) || it.title.contains(searchQuery, ignoreCase = true)
            }
        } else {
            favoriteProducts
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GardropsTopBar(
                navController = navController,
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                showSearchBar = true,
                favoriteItemCount = favoriteProductIds.size,
                cartItemCount = cartItemCount
            )
        },
        bottomBar = { GardropsBottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Favorilerim",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            if (filteredFavorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isNotBlank()) "Arama sonucu bulunamadı." else "Henüz favori ürününüz yok.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredFavorites) { product ->
                        ProductCard(
                            product = product,
                            isFavorited = product.id in favoriteProductIds,
                            onFavoriteClick = { onFavoriteClick(product.id) },
                            onCardClick = { navController.navigate("productDetail/${product.id}") }
                        )
                    }
                }
            }
        }
    }
}