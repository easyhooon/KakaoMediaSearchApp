package com.kenshi.data.local.db

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kenshi.data.local.dao.KakaoMediaSearchDao

@TypeConverters(OrmConvertor::class)
abstract class KakaoMediaSearchDatabase : RoomDatabase() {

    // abstract fun kakaoMediaSearchDao(): KakaoMediaSearchDao
}