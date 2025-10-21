package com.zsd.gardropapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = GardropsPink,          // Ana eylem rengi (butonlar, tab indicator)
    background = GardropsLightGray,  // Uygulamanın ana arka plan rengi
    surface = GardropsWhite,         // Kartların, App Bar'ların yüzey rengi
    onPrimary = GardropsWhite,       // Ana rengin üzerindeki metin/icon rengi (pembe buton üzeri beyaz yazı)
    onBackground = GardropsDarkGray, // Ana arka planın üzerindeki metin rengi
    onSurface = GardropsDarkGray,    // Kartların üzerindeki metin rengi
    onSurfaceVariant = GardropsGray, // İkincil metinler ve aktif olmayan ikonlar için
    surfaceVariant = SearchBarGray   // Arama çubuğu gibi farklı tondaki yüzeyler
)

@Composable
fun GardropappTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}