package com.kenshi.data.mapper

import android.annotation.SuppressLint
import com.kenshi.data.model.image.KakaoImageSearchResponse
import com.kenshi.data.model.video.KakaoVideoSearchResponse
import com.kenshi.domain.entity.search.KakaoMediaSearchEntity
import com.kenshi.domain.model.KakaoMediaSearchInfo
import com.kenshi.domain.model.KakaoMediaSearchType

@SuppressLint("NewApi")
fun KakaoImageSearchResponse.toEntity() = KakaoMediaSearchEntity(
    isEnd = this.meta.isEnd,
    itemList = this.documents.map { document ->
        KakaoMediaSearchInfo(
            title = document.siteName,
            url = document.url,
            originalUrl = document.imageUrl,
            thumbnailUrl = document.thumbnailUrl,
            dateTime = document.datetime,
            mediaType = KakaoMediaSearchType.IMAGE
        )
    }
)

fun KakaoVideoSearchResponse.toEntity() = KakaoMediaSearchEntity(
    isEnd = this.meta.isEnd,
    itemList = this.documents.map { document ->
        KakaoMediaSearchInfo(
            title = document.title,
            url = document.url,
            thumbnailUrl = document.thumbnailUrl,
            dateTime = document.datetime,
            mediaType = KakaoMediaSearchType.VIDEO
        )
    }
)