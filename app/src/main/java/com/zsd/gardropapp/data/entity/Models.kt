package com.zsd.gardropapp.data.entity

data class Comment(
    val userName: String,
    val rating: Float,
    val text: String
)

data class Product(
    val id: Int,
    val title: String,
    val userName: String,
    val userProfileImage: Int,
    val price: String,
    val likes: Int,
    val isFreeShipping: Boolean,
    val isPromo: Boolean = false,
    val isPremium: Boolean = false,
    val description: String,
    val images: List<Int>,
    val comments: List<Comment>
)