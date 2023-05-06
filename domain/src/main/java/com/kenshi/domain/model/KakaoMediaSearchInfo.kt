package com.kenshi.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

data class KakaoMediaSearchInfo(
    val title: String,
    val url: String,
    val originalUrl: String? = null,
    val thumbnailUrl: String,
    val dateTime: Instant,
    val mediaType: KakaoMediaSearchType,
)

enum class KakaoMediaSearchType {
    IMAGE,
    VIDEO
}