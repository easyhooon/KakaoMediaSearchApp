package com.kenshi.data.di

import android.content.Context
import androidx.room.Room
import com.kenshi.data.local.db.KakaoMediaSearchDatabase
import com.kenshi.util.Constants.MEDIA_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KakaoMediaSearchDatabase {
        return Room.databaseBuilder(
            context,
            KakaoMediaSearchDatabase::class.java,
            MEDIA_DATABASE
        ).build()
    }
}