package com.zsd.gardropapp.data.repository

import com.zsd.gardropapp.R
import com.zsd.gardropapp.data.entity.Comment
import com.zsd.gardropapp.data.entity.Product

class ProductRepository {
    fun getDummyProducts(): List<Product> {
        return listOf(
            Product(id = 0, title = "", userName = "", price = "", likes = 0, isFreeShipping = false, isPromo = true, description = "", images = emptyList(), comments = emptyList(), userProfileImage = 0),
            Product(id = 1, title = "Kot Ceket", userName = "ayurahu", userProfileImage = R.drawable.profil1, price = "250 ₺", likes = 45, isFreeShipping = true, isPremium = false, description = "Zara marka, klasik mavi kot ceket. Her dolabın vazgeçilmezi, zamansız bir parça.", images = listOf(R.drawable.ilan1, R.drawable.ilan1), comments = listOf(Comment("zeynep", 4.5f, "Çok beğendim, tam göründüğü gibi!"), Comment("ahmet", 5.0f, "Hızlı kargo için teşekkürler."))),
            Product(id = 2, title = "Pembe Şişme Mont", userName = "ckmkbusra", userProfileImage = R.drawable.profil2, price = "300 ₺", likes = 80, isFreeShipping = false, isPremium = true, description = "Bershka marka, pembe şişme mont. Soğuk kış günleri için ideal, çok sıcak tutuyor.", images = listOf(R.drawable.ilan2, R.drawable.ilan2), comments = listOf(Comment("selin", 5.0f, "Rengi harika, bayıldım!"), Comment("merve", 4.8f, "Kumaşı çok kaliteli."))),
            Product(id = 3, title = "Kürklü Biker Ceket", userName = "turgutt01", userProfileImage = R.drawable.profil1, price = "450 ₺", likes = 15, isFreeShipping = true, isPremium = true, description = "Krem rengi, suni kürklü biker ceket. Siyah deri detaylı, çok şık ve sıcak tutan bir model.", images = listOf(R.drawable.ilan3, R.drawable.ilan3), comments = listOf(Comment("can", 5.0f, "Sorunsuz alışveriş, ürün fotoğraftakinden bile güzel."))),
            Product(id = 4, title = "Bej Şişme Mont", userName = "fashionista", userProfileImage = R.drawable.profil2, price = "280 ₺", likes = 60, isFreeShipping = true, isPremium = false, description = "Bej rengi, oversize şişme mont. Rahat ve stil sahibi bir görünüm için mükemmel.", images = listOf(R.drawable.ilan4, R.drawable.ilan4), comments = emptyList()),
            Product(id = 5, title = "Gri Mom Jean", userName = "stylequeen", userProfileImage = R.drawable.profil1, price = "180 ₺", likes = 95, isFreeShipping = false, isPremium = false, description = "Gri mom jean, paçaları yıpratma detaylı. Yüksek bel, trend bir parça.", images = listOf(R.drawable.ilan5, R.drawable.ilan5), comments = listOf(Comment("ali", 5.0f, "Mükemmel kalıp."), Comment("veli", 4.8f, "Çok şık duruyor."))),
            Product(id = 6, title = "Boru Paça Kot", userName = "jeanseven", userProfileImage = R.drawable.profil2, price = "220 ₺", likes = 30, isFreeShipping = true, isPremium = true, description = "Koyu renk, boru paça kot pantolon. Her şeyle kolayca kombinlenebilir, dolabınızın kurtarıcısı.", images = listOf(R.drawable.ilan6, R.drawable.ilan6), comments = listOf(Comment("ayşe", 5.0f, "Tam aradığım gibi bir pantolon.")))
        )
    }
}