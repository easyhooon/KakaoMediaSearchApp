package com.kenshi.util

sealed class Screen(val route: String) {
    object Search : Screen(route = "search")
    object Favorites : Screen(route = "favorites")
}
