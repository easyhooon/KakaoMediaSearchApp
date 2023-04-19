package com.kenshi.kakaomediasearch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kenshi.favorites.navigation.favoritesRoute
import com.kenshi.search.navigation.searchRoute

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        searchRoute()
        favoritesRoute()
    }
}