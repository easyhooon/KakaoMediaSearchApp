package com.kenshi.data.di

import com.kenshi.data.service.KakaoMediaSearchService
import com.kenshi.data.source.remote.KakaoMediaSearchRemoteDataSource
import com.kenshi.data.source.remote.KakaoMediaSearchRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideKakaoMediaSearchRemoteDataSource(
        kakaoMediaSearchService: KakaoMediaSearchService
    ): KakaoMediaSearchRemoteDataSource {
        return KakaoMediaSearchRemoteDataSourceImpl(kakaoMediaSearchService)
    }
}