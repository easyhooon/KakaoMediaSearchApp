package com.kenshi.search.component

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.kenshi.search.SearchUiState
import com.kenshi.ui.components.LoadingScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    mediaSearchListState: MediaSearchListState,
    onSearchClick: (String) -> Unit,
    onNextPage: (String, Int) -> Unit,
    onClear: () -> Unit,
    onError: (String) -> Unit
) {
    val uiState = remember(searchUiState) {
        derivedStateOf {
            searchUiState
        }
    }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    val uriHandler = LocalUriHandler.current

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val onSearchEvent = {
        if (searchQuery.isEmpty()) {
            Toast.makeText(context, "검색어를 입력 해주세요", Toast.LENGTH_SHORT).show()
            // onError(queryLengthErrorMessage)
        } else if (searchUiState != SearchUiState.LOADING) {
            onSearchClick(searchQuery)
            //showBackButton = true
            coroutineScope.launch {
                listState.scrollToItem(0)
            }
        }
    }

    Scaffold(
        topBar = {
            //TODO 이거 왜 TopBar 로 감싸져있지 (프리뷰를 위해?)
            SearchTopBar(
                query = searchQuery,
                onTextChange = { text ->
                    searchQuery = text
                },
                onSearchClick = onSearchEvent
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                when (uiState.value) {
                    SearchUiState.IDLE -> {
                        SearchEmptyScreen()
                    }

                    SearchUiState.LOADING -> {
                        LoadingScreen()
                    }

                    SearchUiState.EMPTY -> {
                        SearchEmptyScreen()
                    }

                    SearchUiState.ERROR -> {
                        SearchErrorScreen()
                    }

                    SearchUiState.SHOW_RESULT -> {
                        SearchListContent(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            // weight 는 Column 내부에서 사용할 수 있음
                            //.weight(1f)
                            listState = listState,
                            mediaSearchList = mediaSearchListState,
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
            }
        }
    )
}