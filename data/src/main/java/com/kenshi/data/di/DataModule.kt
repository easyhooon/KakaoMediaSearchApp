package com.kenshi.data.di

import com.kenshi.data.source.remote.KakaoMediaSearchRemoteDataSource
import com.kenshi.data.source.remote.KakaoMediaSearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindKakaoMediaSearchRemoteDataSource(kakaoMediaSearchRemoteDataSourceImpl: KakaoMediaSearchRemoteDataSourceImpl): KakaoMediaSearchRemoteDataSource
}