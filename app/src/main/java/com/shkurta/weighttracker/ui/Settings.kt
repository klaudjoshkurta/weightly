package com.shkurta.weighttracker.ui

enum class AppTheme(
    val label: String,
    val storageKey: String
) {
    LIGHT("Light", "light"),
    DARK("Dark", "dark"),
    AUTO("Auto", "auto");

    companion object {
        fun fromStorageKey(value: String?): AppTheme {
            return when (value) {
                "dark" -> DARK
                "light" -> LIGHT
                "auto" -> AUTO
                else -> AUTO // default
            }
        }
    }
}