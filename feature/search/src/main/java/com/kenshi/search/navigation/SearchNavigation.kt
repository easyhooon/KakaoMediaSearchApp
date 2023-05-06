package com.kenshi.search.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kenshi.search.SearchViewModel
import com.kenshi.search.component.SearchScreen
import com.kenshi.util.Screen

fun NavGraphBuilder.searchRoute(
    showSnackbar: (String) -> Unit
) {
    composable(route = Screen.Search.route) {
        // 올바른 뷰모델의 위치
        val viewModel: SearchViewModel = hiltViewModel()

        val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
        val searchMediaList by viewModel.searchMediaList.collectAsStateWithLifecycle()

        SearchScreen(
            searchUiState = searchUiState,
            mediaSearchListState = searchMediaList,
            onSearchClick = { query ->
                viewModel.onSearchClick(query)
            },
            onNextPage = { query, page ->
                viewModel.onNextPage(query, page)
            },
            onClear = viewModel::onClear,
            onError = showSnackbar
        )
    }
}