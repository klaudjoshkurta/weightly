package com.shkurta.weighttracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.shkurta.weighttracker.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val dmMono = GoogleFont("DM Mono")

val fontFamily = FontFamily(
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Thin),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.ExtraLight),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Light),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.ExtraBold),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Black),
    Font(googleFont = dmMono, fontProvider = provider, weight = FontWeight.Black, style = FontStyle.Italic)
)

val Typography = Typography()