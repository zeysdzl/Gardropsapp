package com.zsd.gardropapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zsd.gardropapp.data.entity.Product
import com.zsd.gardropapp.ui.components.*

@Composable
fun MainScreen(
    navController: NavController,
    products: List<Product>,
    favoriteProductIds: List<Int>,
    onFavoriteClick: (Int) -> Unit,
    cartItemCount: Int
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val displayedProducts = remember(selectedTabIndex, products, searchQuery) {
        val productsByTab = when (selectedTabIndex) {
            0 -> products.filter { !it.isPremium }
            1 -> products.filter { it.isPremium }
            else -> emptyList()
        }
        if (searchQuery.length >= 2) {
            productsByTab.filter {
                it.description.contains(searchQuery, ignoreCase = true) || it.title.contains(searchQuery, ignoreCase = true)
            }
        } else {
            productsByTab
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
        Column(modifier = Modifier.padding(paddingValues)) {
            GardropsTabs(selectedTabIndex = selectedTabIndex, onTabSelected = { selectedTabIndex = it })
            ProductGrid(
                navController = navController,
                products = displayedProducts,
                favoriteProductIds = favoriteProductIds,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
private fun GardropsTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Senin için", "Premium")
    val accentColor = MaterialTheme.colorScheme.primary

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = accentColor,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 3.dp,
                color = accentColor
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTabIndex == index) accentColor else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}

@Composable
private fun ProductGrid(
    navController: NavController,
    products: List<Product>,
    favoriteProductIds: List<Int>,
    onFavoriteClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            if (product.isPromo) {
                PromotionCard()
            } else {
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