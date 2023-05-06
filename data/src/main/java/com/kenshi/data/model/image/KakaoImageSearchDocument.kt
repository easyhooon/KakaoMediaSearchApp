package com.kenshi.data.model.image

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 기존의 자바의 Date 를, kotlin Serialization 적용을 위해 kotlin 의 Instant 로 변경
// kotlinx-datetime 라이브러리 적용
@Serializable
data class KakaoImageSearchDocument(
    @SerialName("collection")
    val collection: String,
    @SerialName("datetime")
    val datetime: Instant,
    @SerialName("display_sitename")
    val siteName: String,
    @SerialName("doc_url")
    val url: String,
    @SerialName("height")
    val height: Int,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("width")
    val width: Int
)
