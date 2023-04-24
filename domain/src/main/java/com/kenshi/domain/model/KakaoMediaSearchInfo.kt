package com.kenshi.domain.model

import kotlinx.datetime.LocalDate

data class KakaoMediaSearchInfo(
    val title: String,
    val url: String,
    val originalUrl: String? = null,
    val thumbnailUrl: String,
    val dateTime: LocalDate,
    val mediaType: KakaoMediaSearchType,
)

enum class KakaoMediaSearchType {
    IMAGE,
    VIDEO
}