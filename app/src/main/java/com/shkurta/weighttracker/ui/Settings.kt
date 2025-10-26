package com.shkurta.weighttracker.ui

enum class AppLanguage(
    val label: String,
    val storageKey: String
) {
    ENGLISH("English", "en"),
    GREEK("Ελληνικά", "el");

    companion object {
        fun fromStorageKey(value: String?): AppLanguage {
            return when (value) {
                "el" -> GREEK
                "en" -> ENGLISH
                else -> ENGLISH
            }
        }
    }
}

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