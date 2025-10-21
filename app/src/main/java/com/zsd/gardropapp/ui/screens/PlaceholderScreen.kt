package com.zsd.gardropapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zsd.gardropapp.ui.components.GardropsBottomNavBar
import com.zsd.gardropapp.ui.components.GardropsTopBar

@Composable
fun PlaceholderScreen(
    screenName: String,
    navController: NavController,
    favoriteItemCount: Int,
    cartItemCount: Int
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GardropsTopBar(
                navController = navController,
                searchQuery = "",
                onQueryChange = {},
                showSearchBar = false,
                favoriteItemCount = favoriteItemCount,
                cartItemCount = cartItemCount
            )
        },
        bottomBar = { GardropsBottomNavBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("This is the $screenName screen", style = MaterialTheme.typography.titleLarge)
        }
    }
}