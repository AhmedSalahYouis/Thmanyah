package com.thamaneya.androidchallenge.core.design

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thamaneya.androidchallenge.core.design.R

/**
 * Custom font family for IBM Plex Sans Arabic
 * This font family supports multiple weights for Arabic text
 */
val IbmPlexSansArabic = FontFamily(
    Font(R.font.ibm_plex_sans_arabic_extralight, FontWeight.ExtraLight),
    Font(R.font.ibm_plex_sans_arabic_light, FontWeight.Light),
    Font(R.font.ibm_plex_sans_arabic_regular, FontWeight.Normal),
    Font(R.font.ibm_plex_sans_arabic_text, FontWeight.Normal),
    Font(R.font.ibm_plex_sans_arabic_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_sans_arabic_semibold, FontWeight.SemiBold),
    Font(R.font.ibm_plex_sans_arabic_bold, FontWeight.Bold)
)

/**
 * App Typography system using IBM Plex Sans Arabic font family
 * Provides consistent text styles across the application
 */
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = IbmPlexSansArabic,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = IbmPlexSansArabic,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
).run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = IbmPlexSansArabic),
        displayMedium = displayMedium.copy(fontFamily = IbmPlexSansArabic),
        displaySmall = displaySmall.copy(fontFamily = IbmPlexSansArabic),
        headlineLarge = headlineLarge.copy(fontFamily = IbmPlexSansArabic),
        headlineMedium = headlineMedium.copy(fontFamily = IbmPlexSansArabic),
        headlineSmall = headlineSmall.copy(fontFamily = IbmPlexSansArabic),
        titleMedium = titleMedium.copy(fontFamily = IbmPlexSansArabic),
        titleSmall = titleSmall.copy(fontFamily = IbmPlexSansArabic),
        bodyMedium = bodyMedium.copy(fontFamily = IbmPlexSansArabic),
        bodySmall = bodySmall.copy(fontFamily = IbmPlexSansArabic),
        labelLarge = labelLarge.copy(fontFamily = IbmPlexSansArabic),
        labelMedium = labelMedium.copy(fontFamily = IbmPlexSansArabic),
        labelSmall = labelSmall.copy(fontFamily = IbmPlexSansArabic),
    )
}
