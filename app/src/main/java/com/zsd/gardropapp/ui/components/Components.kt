package com.zsd.gardropapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zsd.gardropapp.R
import com.zsd.gardropapp.data.entity.Comment
import com.zsd.gardropapp.data.entity.Product
import com.zsd.gardropapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardropsTopBar(
    navController: NavController,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    showSearchBar: Boolean = false,
    favoriteItemCount: Int,
    cartItemCount: Int
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 21.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.gardrops),
                contentDescription = "Gardrops Logo",
                modifier = Modifier.height(40.dp)
            )
            Row {
                BadgedBox(badge = { if (favoriteItemCount > 0) { Badge { Text("$favoriteItemCount") } } }) {
                    IconButton(
                        onClick = { navController.navigate("favorites") },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = if (currentRoute == "favorites") MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                BadgedBox(badge = { if (cartItemCount > 0) { Badge { Text("$cartItemCount") } } }) {
                    IconButton(
                        onClick = { navController.navigate("cart") },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = if (currentRoute == "cart") MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                    ) {
                        Icon(Icons.Outlined.ShoppingCart, contentDescription = "Shopping Bag", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        if (showSearchBar) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                placeholder = { Text("Kurtaran parça kot şortlar", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = CircleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    isFavorited: Boolean,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onCardClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = product.userProfileImage), contentDescription = "${product.userName} profile picture", contentScale = ContentScale.Crop, modifier = Modifier.size(24.dp).clip(CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = product.userName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
            }
            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f).padding(8.dp).clip(RoundedCornerShape(8.dp))) {
                Image(painter = painterResource(id = product.images.firstOrNull() ?: R.drawable.ilan1), contentDescription = product.description, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                if (product.isFreeShipping) {
                    Text("Kargo Bedava", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)).padding(vertical = 4.dp))
                }
            }
            Column(Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(text = product.title, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, maxLines = 1)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = product.price, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    IconToggleButton(checked = isFavorited, onCheckedChange = { onFavoriteClick() }) {
                        Icon(imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Likes", tint = if (isFavorited) GardropsRed else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun GardropsBottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val activeColor = MaterialTheme.colorScheme.surfaceVariant

    BottomAppBar(
        modifier = Modifier.height(70.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("main") }, colors = IconButtonDefaults.iconButtonColors(containerColor = if (currentRoute == "main") activeColor else Color.Transparent)) {
                Image(painter = painterResource(id = R.drawable.g), contentDescription = "Home", modifier = Modifier.size(24.dp))
            }
            IconButton(onClick = { navController.navigate("search") }, colors = IconButtonDefaults.iconButtonColors(containerColor = if (currentRoute == "search") activeColor else Color.Transparent)) { Icon(Icons.Default.Search, contentDescription = "Search") }
            IconButton(onClick = { navController.navigate("add") }, colors = IconButtonDefaults.iconButtonColors(containerColor = if (currentRoute == "add") activeColor else Color.Transparent)) { Icon(Icons.Outlined.AddCircle, contentDescription = "Add", modifier = Modifier.size(32.dp)) }
            IconButton(onClick = { navController.navigate("chat") }, colors = IconButtonDefaults.iconButtonColors(containerColor = if (currentRoute == "chat") activeColor else Color.Transparent)) { Icon(Icons.Outlined.Email, contentDescription = "Chat") }
            IconButton(onClick = { navController.navigate("profile") }, colors = IconButtonDefaults.iconButtonColors(containerColor = if (currentRoute == "profile") activeColor else Color.Transparent)) { Icon(Icons.Outlined.Person, contentDescription = "Profile") }
        }
    }
}

@Composable
fun StarRating(rating: Float, starCount: Int = 5, starColor: Color = GardropsStarYellow) {
    Row {
        repeat(starCount) { i ->
            val icon = if (i < rating) Icons.Filled.Star else Icons.Default.Star
            Icon(icon, contentDescription = null, tint = starColor, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(comment.userName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            StarRating(rating = comment.rating)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(comment.text, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun CartItem(product: Product, onRemoveClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = product.images.first()), contentDescription = product.title, contentScale = ContentScale.Crop, modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(product.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Text(product.price, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        IconButton(onClick = onRemoveClick) {
            Icon(Icons.Default.Delete, contentDescription = "Remove item", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun PromotionCard() {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = PromoCardBackground)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxHeight(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
            Text("gardrops'ta", color = PromoCardText, style = MaterialTheme.typography.titleMedium)
            Text("%0 Komisyonla", color = PromoCardText, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Kazancının", color = PromoCardText, style = MaterialTheme.typography.titleMedium)
            Text("Hepsi Senin!", color = PromoCardText, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Üstelik", color = PromoCardText, style = MaterialTheme.typography.bodyMedium)
            Text("Kargo Bedava", color = PromoCardText, style = MaterialTheme.typography.titleMedium)
        }
    }
}