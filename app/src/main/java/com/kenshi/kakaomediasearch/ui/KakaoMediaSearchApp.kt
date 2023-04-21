package com.kenshi.kakaomediasearch.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kenshi.kakaomediasearch.navigation.NavBarItems
import com.kenshi.kakaomediasearch.navigation.SetupNavGraph
import com.kenshi.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KakaoMediaSearchApp() {
    val navHostController = rememberNavController()
    val backStackEntry by navHostController.currentBackStackEntryAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            androidx.compose.material3.NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                NavBarItems.BarItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = backStackEntry?.destination?.route == navItem.route,
                        onClick = {
                            navHostController.navigate(navItem.route) {
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.image,
                                contentDescription = navItem.title
                            )
                        },
                        label = {
                            Text(text = navItem.title)
                        }
                    )
                }
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            SetupNavGraph(
                modifier = Modifier.fillMaxSize(),
                navController = navHostController,
                startDestination = Screen.Search.route,
                snackbarHostState = snackbarHostState
            )
        }
    }
}