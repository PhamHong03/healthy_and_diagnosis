package com.example.hd_project.domain.model

data class MenuItemData(
    val title: String,
    val iconRes: Int,
    val destination: String,
    val badgeCount: String? = null
)


