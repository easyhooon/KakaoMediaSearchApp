package com.kenshi.data.di

import com.kenshi.data.repository.KakaoMediaSearchRepositoryImpl
import com.kenshi.domain.repository.KakaoMediaSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideKakaoMediaSearchRepository(kakaoMediaSearchRepositoryImpl: KakaoMediaSearchRepositoryImpl): KakaoMediaSearchRepository
}

//@Module
//@InstallIn(SingletonComponent::class)
//interface RepositoryModule {
//
//    @Binds
//    @Singleton
//    fun provideKakaoMediaSearchRepository(kakaoMediaSearchRepositoryImpl: KakaoMediaSearchRepositoryImpl): KakaoMediaSearchRepository
//}