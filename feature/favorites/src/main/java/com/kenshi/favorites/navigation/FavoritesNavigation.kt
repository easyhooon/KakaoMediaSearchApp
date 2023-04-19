package com.kenshi.favorites.navigation


import com.kenshi.util.Screen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kenshi.favorites.FavoritesScreen

fun NavGraphBuilder.favoritesRoute(

) {
    composable(route = Screen.Favorites.route) {
        FavoritesScreen()
    }
}