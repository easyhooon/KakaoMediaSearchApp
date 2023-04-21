package com.kenshi.domain.model

import kotlinx.datetime.LocalDate


data class KakaoMediaSearchModel(
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
