package com.kenshi.domain.repository

import com.kenshi.domain.entity.search.KakaoMediaSearchEntity
import com.kenshi.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface KakaoMediaSearchRepository {

    // Image, Video 함수의 반환 타입을 공통 타입으로 변환
    fun getKakaoImageSearchList(
        query: String,
        sortType: KakaoMediaSearchSortType,
        page: Int,
        size: Int,
    ): Flow<ApiResult<KakaoMediaSearchEntity>>

    fun getKakaoVideoSearchList(
        query: String,
        sortType: KakaoMediaSearchSortType,
        page: Int,
        size: Int
    ): Flow<ApiResult<KakaoMediaSearchEntity>>
}

enum class KakaoMediaSearchSortType(val value: String) {
    ACCURAY("accuracy"),
    RECENCY("recency")
}