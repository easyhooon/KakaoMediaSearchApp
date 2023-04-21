package com.kenshi.data.service

import com.kenshi.data.model.image.KakaoImageSearchResponse
import com.kenshi.data.model.video.KakaoVideoSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoMediaSearchService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getKakaoImageSearchResponse(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): KakaoImageSearchResponse {
//        return try {
//            val response = httpClient.get("/v2/search/image") {
//                parameter("query", query)
//                parameter("sort", sort)
//                parameter("page", page)
//                parameter("size", size)
//            }.body<HttpResponse>()
//            if (response.status.isSuccess()) {
//                ApiResult.Success(response.body())
//            } else {
//                ApiResult.Error(response.status.value, response.status.description)
//            }
//        } catch (e: Exception) {
//            ApiResult.Exception(e)
//        }
        return httpClient.get("/v2/search/image") {
            parameter("query", query)
            parameter("sort", sort)
            parameter("page", page)
            parameter("size", size)
        }.body()
    }

    suspend fun getKakaoVideoSearchResponse(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): KakaoVideoSearchResponse {
//        return try {
//            val response = httpClient.get("/v2/search/vclip") {
//                parameter("query", query)
//                parameter("sort", sort)
//                parameter("page", page)
//                parameter("size", size)
//            }.body<HttpResponse>()
//            if (response.status.isSuccess()) {
//                ApiResult.Success(response.body())
//            } else {
//                ApiResult.Error(response.status.value, response.status.description)
//            }
//        } catch (e: Exception) {
//            ApiResult.Exception(e)
//        }
        return httpClient.get("/v2/search/vclip") {
            parameter("query", query)
            parameter("sort", sort)
            parameter("page", page)
            parameter("size", size)
        }.body()
    }
}


