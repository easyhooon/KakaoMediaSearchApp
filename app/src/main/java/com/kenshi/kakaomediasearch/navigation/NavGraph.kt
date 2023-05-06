package com.kenshi.kakaomediasearch.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kenshi.favorites.navigation.favoritesRoute
import com.kenshi.search.navigation.searchRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        searchRoute(
            showSnackbar = { errorMsg ->
                snackbarHostState.showMessage(coroutineScope, errorMsg)
            }
        )
        favoritesRoute()
    }
}

private fun SnackbarHostState.showMessage(
    coroutineScope: CoroutineScope,
    text: String,
) {
    coroutineScope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(text)
    }
}