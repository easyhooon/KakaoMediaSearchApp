package com.kenshi.search.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.kenshi.search.SearchUiState
import com.kenshi.ui.components.LoadingWheel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    mediaSearchListState: mediaSearchListState,
    onSearchClick: (String) -> Unit,
    onNextPage: (String, Int) -> Unit,
    onClear: () -> Unit,
    onError: (String) -> Unit
) {

//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Icon(
//            imageVector = Icons.Filled.Search,
//            contentDescription = "search",
//            modifier = Modifier.size(120.dp)
//                .align(Alignment.Center)
//        )
//    }

    val uiState = remember(searchUiState) {
        derivedStateOf {
            searchUiState
        }
    }

    var padding by remember { mutableStateOf(PaddingValues()) }

    var searchQuery by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            //TODO 이거 왜 TopBar 로 감싸져있지 (프리뷰를 위해?)
            SearchTopBar(
                query = searchQuery,
                onTextChange = { text ->
                    searchQuery = text
                },
                onSearchClick = { query ->
                    onSearchClick(query)
                }
            )
        },
        content = {
            padding = it

            when (uiState.value) {
                SearchUiState.IDLE -> {
                    SearchEmptyScreen()
                }

                SearchUiState.LOADING -> {
                    LoadingWheel()
                }

                SearchUiState.EMPTY -> {
                    SearchEmptyScreen()
                }

                SearchUiState.ERROR -> {
                    SearchEmptyScreen()
                }

                SearchUiState.SHOW_RESULT -> {
                    SearchListContent(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                            // weight 는 Column 내부에서 사용할 수 있음
                            //.weight(1f)
                        listState = listState,
                        searchMediaList = mediaSearchListState,
                        onNextPage = { page ->
                            onNextPage(searchQuery, page)
                        },
                        onClickLink = { mediaDetailData ->
                            uriHandler.openUri(mediaDetailData.mediaInfo.url)
                        },
                        onClickFavorite = {}
                    )
                }
            }

            SearchListContent(
                searchMediaList = mediaSearchListState,
                listState = listState,
                onNextPage = { page ->
                    onNextPage(searchQuery, page)
                },
                onClickLink = {},
                onClickFavorite = {},
            )
        }
    )
}