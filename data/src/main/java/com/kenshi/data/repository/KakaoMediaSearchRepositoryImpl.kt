package com.kenshi.data.repository

import com.kenshi.data.mapper.toDomain
import com.kenshi.data.source.remote.KakaoMediaSearchRemoteDataSource
import com.kenshi.data.util.safeFlow
import com.kenshi.domain.entity.search.KakaoMediaSearchEntity
import com.kenshi.domain.repository.KakaoMediaSearchRepository
import com.kenshi.domain.repository.KakaoMediaSearchSortType
import com.kenshi.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// repositoryImpl 에서 mapper 를 적용해 줄 수 있는 방법
class KakaoMediaSearchRepositoryImpl @Inject constructor(
    private val kakaoMediaSearchRemoteDataSource: KakaoMediaSearchRemoteDataSource
):KakaoMediaSearchRepository {
    override fun getKakaoImageSearchList(
        query: String,
        sortType: KakaoMediaSearchSortType,
        page: Int,
        size: Int
    ): Flow<ApiResult<KakaoMediaSearchEntity>> {
        return safeFlow {
            kakaoMediaSearchRemoteDataSource.getKakaoImageSearch(query, sortType.value, page, size).toDomain()
        }
    }

    override fun getKakaoVideoSearchList(
        query: String,
        sortType: KakaoMediaSearchSortType,
        page: Int,
        size: Int
    ): Flow<ApiResult<KakaoMediaSearchEntity>> {
        return safeFlow {
            kakaoMediaSearchRemoteDataSource.getKakaoVideoSearch(query, sortType.value, page, size).toDomain()
        }
    }
}