package com.kenshi.domain.usecase.search

import com.kenshi.domain.entity.search.KakaoMediaSearchEntity
import com.kenshi.domain.model.KakaoMediaSearchInfo
import com.kenshi.domain.model.KakaoMediaSearchItem
import com.kenshi.domain.model.KakaoMediaSearchModel
import com.kenshi.domain.repository.KakaoMediaSearchRepository
import com.kenshi.domain.repository.KakaoMediaSearchSortType
import com.kenshi.domain.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private data class KakaoMediaSearchApiState(
    var nextPage: Int = 1,
    var pageable: Boolean = true,
    var mediaList: MutableList<KakaoMediaSearchInfo> = mutableListOf(),
    var apiStream: Flow<ApiResult<KakaoMediaSearchEntity>>? = null
)

// 두 api 호출하여 그 결과를 최신순으로 정렬한 후에 paging 처리를 하여 반환
class GetKakaoMediaSearchSortedListUseCase @Inject constructor(
    private val kakaoMediaSearchRepository: KakaoMediaSearchRepository
) {
    private var currentPageSize: Int = 30
    private var currentQuery: String = ""

    private var imageSearchApiState: KakaoMediaSearchApiState = KakaoMediaSearchApiState()
    private var videoSearchApiState: KakaoMediaSearchApiState = KakaoMediaSearchApiState()

    private val kakaoMediaSearchSortedList = mutableListOf<KakaoMediaSearchInfo>()

    // 이미지 및 비디오 검색 결과를 정렬하여 가져옵니다.
    // 1. 쿼리가 변경되거나 새로고침이 필요한 경우 상태 및 목록을 초기화합니다
    // 2. 이미지 및 비디오 검색 결과를 결합하고 정렬합니다
    // 3. 즐겨찾기 목록을 가져옵니다.
    // 4. 검색 결과의 각 항목에 대해 즐겨찾기 여부를 확인하고, 최종 결과를 생성합니다.
    // 5. 검색 결과가 더이상 없거나 페이지 제한에 도달한 경우, ApiResult.Excecption을 반환합니다.
    operator fun invoke(
        page: Int,
        pageSize: Int = currentPageSize,
        query: String = currentQuery,
        refresh: Boolean = false,
    ): Flow<ApiResult<KakaoMediaSearchModel>> = channelFlow {
        if (currentQuery != query || refresh) {
            currentPageSize = pageSize
            currentQuery = query
            imageSearchApiState = KakaoMediaSearchApiState()
            videoSearchApiState = KakaoMediaSearchApiState()
            kakaoMediaSearchSortedList.clear()
        }

        // 이미지 및 비디오 검색 결과를 결합
        while (kakaoMediaSearchSortedList.size < page * pageSize) {
            // Check if both image and video are not pageable
            if (!imageSearchApiState.pageable && !videoSearchApiState.pageable) {
                break
            }

            //TODO 검색 방식 전환 할 수 있도록 변경

            // 겸색 결과를 처리하고 정렬하여 반환
            imageSearchApiState.apiStream = kakaoMediaSearchRepository.getKakaoImageSearchList(
                query = query,
                sortType = KakaoMediaSearchSortType.RECENCY,
                page = imageSearchApiState.nextPage,
                size = 80
            )
            videoSearchApiState.apiStream = kakaoMediaSearchRepository.getKakaoVideoSearchList(
                query = query,
                sortType = KakaoMediaSearchSortType.RECENCY,
                page = videoSearchApiState.nextPage,
                size = 30
            )

            withContext(Dispatchers.IO) {
                listOf(
                    imageSearchApiState,
                    videoSearchApiState
                ).forEach { apiState ->
                    launch {
                        apiState.apply {
                            // Get image search result
                            if (pageable) {
                                if (mediaList.isNotEmpty()) {
                                    return@launch
                                }
                                apiStream?.collectLatest { apiResult ->
                                    when (apiResult) {
                                        is ApiResult.Success -> {
                                            mediaList.addAll(apiResult.data.itemList)
                                            nextPage += 1
                                            if (apiResult.data.isEnd) {
                                                pageable = false
                                            }
                                        }
                                        is ApiResult.Error -> {
                                            send(apiResult)
                                            this@channelFlow.cancel()
                                        }
                                        is ApiResult.Exception -> {
                                            send(apiResult)
                                            this@channelFlow.cancel()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Check api limit page
            if (imageSearchApiState.nextPage > ImagePageLimit) {
                imageSearchApiState.pageable = false
            }
            if (videoSearchApiState.nextPage > VideoPageLimit) {
                videoSearchApiState.pageable = false
            }

            val tempMediaList = mutableListOf<KakaoMediaSearchInfo>()
            lateinit var hasLessDateType: MutableList<KakaoMediaSearchInfo>
            lateinit var hasMoreDateType: MutableList<KakaoMediaSearchInfo>

            val imageList = imageSearchApiState.mediaList
            val videoList = videoSearchApiState.mediaList

            // 아무리 봐도 반대 같은데
            when {
                imageList.isEmpty() && videoList.isEmpty() -> {
                    break
                }

                imageList.isEmpty() -> {
                    hasLessDateType = videoList
                    hasMoreDateType = mutableListOf()
                }

                videoList.isEmpty() -> {
                    hasLessDateType = imageList
                    hasMoreDateType = mutableListOf()
                }

                imageList.last().dateTime > videoList.last().dateTime -> {
                    hasLessDateType = imageList
                    hasMoreDateType = videoList
                }

                else -> {
                    hasLessDateType = videoList
                    hasMoreDateType = imageList
                }
            }
            val lastDateTime = hasLessDateType.last().dateTime

            // hasLessDateType 의 리스트를 tempMediaList 에 넣어주고, hasLessonDateType 은 clear
            tempMediaList.addAll(hasLessDateType)
            hasLessDateType.clear()

            // hasMoreDateType 의 리스트 중 lastDateTime 보다 큰 원소만 tempMediaList 에 넣어주고, hasMoreDateType 은 clear
            hasMoreDateType.toList().forEach {
                if (it.dateTime > lastDateTime) {
                    tempMediaList.add(it)
                    hasMoreDateType.remove(it)
                }
            }

            // tempMediaList 를 dateTime 기준 내림차순(최신순)으로 정렬
            tempMediaList.sortByDescending {
                it.dateTime
            }

            kakaoMediaSearchSortedList.addAll(tempMediaList)
        }

        //TODO 로컬 DB 에서 즐겨찾기 목록을 가져와서 즐겨찾기 된 이미지 상태 변경
        val favoriteList = getFavoriteList()

        // 결과 내보내기
        if (kakaoMediaSearchSortedList.size >= (page - 1) * pageSize) {
            val sortedList: List<KakaoMediaSearchItem> = kakaoMediaSearchSortedList
                .subList(0, (page * pageSize).coerceAtMost(kakaoMediaSearchSortedList.size))
                .map {
                    KakaoMediaSearchItem(
                        favoriteList.contains(it),
                        it
                    )
                }
            if (imageSearchApiState.pageable || videoSearchApiState.pageable) {
                send(
                    ApiResult.Success(
                        KakaoMediaSearchModel(
                            isEnd = false,
                            itemList = sortedList
                        )
                    )
                )
            } else {
                send(
                    ApiResult.Success(
                        KakaoMediaSearchModel(
                            isEnd = true,
                            itemList = sortedList
                        )
                    )
                )
            }
        } else {
            send(ApiResult.Exception(Exception("No more data")))
        }
    }

    // 즐겨찾기 목록을 가져오는 함수
    private suspend fun getFavoriteList(): List<KakaoMediaSearchInfo> {
        // return kakaoMediaSearchRepository.getKakaoFavoriteItemList()
        return listOf()
    }

    companion object {
        const private val ImagePageLimit = 50
        const private val VideoPageLimit = 15
    }
}