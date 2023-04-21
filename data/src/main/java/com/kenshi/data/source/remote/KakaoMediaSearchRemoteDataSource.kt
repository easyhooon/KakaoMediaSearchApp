package com.kenshi.data.source.remote

import com.kenshi.data.model.image.KakaoImageSearchResponse
import com.kenshi.data.model.video.KakaoVideoSearchResponse

interface KakaoMediaSearchRemoteDataSource {
    suspend fun getKakaoImageSearch(query: String, sort: String, page: Int, size: Int): KakaoImageSearchResponse

    suspend fun getKakaoVideoSearch(query: String, sort: String, page: Int, size: Int): KakaoVideoSearchResponse
}