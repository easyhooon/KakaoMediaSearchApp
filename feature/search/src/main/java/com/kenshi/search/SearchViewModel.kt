package com.kenshi.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.domain.usecase.search.GetKakaoMediaSearchSortedListUseCase
import com.kenshi.domain.util.ApiResult
import com.kenshi.search.component.mediaSearchListState
import com.kenshi.util.Constants.KAKAO_SEARCH_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class SearchUiState {
    IDLE,
    LOADING,
    EMPTY,
    SHOW_RESULT,
    ERROR
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getKakaoMediaSearchSortedListUseCase: GetKakaoMediaSearchSortedListUseCase
) : ViewModel() {

    private val _searchUiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.IDLE)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _searchMediaList: MutableStateFlow<mediaSearchListState> =
        MutableStateFlow(mediaSearchListState("", false, 1, listOf()))
    val searchMediaList: StateFlow<mediaSearchListState> = _searchMediaList.asStateFlow()

    fun onSearchClick(query: String) {
        _searchUiState.value = SearchUiState.LOADING
        if (query.isNotEmpty()) {
            getMediaSearchList(query, 1, true)
        }
    }

    private fun getMediaSearchList(query: String, totalPage: Int, refresh: Boolean = false) {
        viewModelScope.launch {
            getKakaoMediaSearchSortedListUseCase(totalPage, KAKAO_SEARCH_PAGE_SIZE, query, refresh).collectLatest { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        if (apiResult.data.itemList.isEmpty()) {
                            _searchMediaList.value = mediaSearchListState(
                                query = "",
                                pageable = false,
                                page = 1,
                                mediaList = listOf()
                            )
                            _searchUiState.value = SearchUiState.EMPTY
                        } else {
                            _searchMediaList.value = mediaSearchListState(
                                query = query,
                                pageable = apiResult.data.isEnd.not(),
                                page = totalPage,
                                mediaList = apiResult.data.itemList
                            )
                            _searchUiState.value = SearchUiState.SHOW_RESULT
                        }
                    }
                    else -> {
                        _searchUiState.value = SearchUiState.ERROR
                    }
                }
            }
        }
    }

    fun onNextPage(query: String, page: Int) {
        getMediaSearchList(query, page)
    }

    // UiState 를 초기화
    fun onClear() {
        _searchUiState.value = SearchUiState.IDLE
        _searchMediaList.value = mediaSearchListState("", false, 1, listOf())
    }
}