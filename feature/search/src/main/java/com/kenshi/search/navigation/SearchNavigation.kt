package com.kenshi.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kenshi.search.SearchScreen
import com.kenshi.util.Screen

fun NavGraphBuilder.searchRoute(
    showSnackbar: (String) -> Unit
) {
    composable(route = Screen.Search.route) {
        SearchScreen(
            showSnackbar = showSnackbar
        )
    }
}