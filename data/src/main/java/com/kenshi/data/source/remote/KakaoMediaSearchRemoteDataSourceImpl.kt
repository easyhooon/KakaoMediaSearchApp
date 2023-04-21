package com.kenshi.data.source.remote

import com.kenshi.data.model.image.KakaoImageSearchResponse
import com.kenshi.data.model.video.KakaoVideoSearchResponse
import com.kenshi.data.service.KakaoMediaSearchService
import javax.inject.Inject

class KakaoMediaSearchRemoteDataSourceImpl @Inject constructor(
    private val kakaoMediaSearchService: KakaoMediaSearchService
) : KakaoMediaSearchRemoteDataSource {
    override suspend fun getKakaoImageSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): KakaoImageSearchResponse {
        return kakaoMediaSearchService.getKakaoImageSearchResponse(query, sort, page, size)
    }

    override suspend fun getKakaoVideoSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): KakaoVideoSearchResponse {
        return kakaoMediaSearchService.getKakaoVideoSearchResponse(query, sort, page, size)
    }
}