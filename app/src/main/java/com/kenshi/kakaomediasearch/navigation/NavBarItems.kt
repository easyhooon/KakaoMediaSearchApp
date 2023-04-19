package com.kenshi.kakaomediasearch.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "검색",
            image = Icons.Filled.Search,
            route = "search"
        ),
        BarItem(
            title = "즐겨찾기",
            image = Icons.Filled.Favorite,
            route = "favorites"
        )
    )
}