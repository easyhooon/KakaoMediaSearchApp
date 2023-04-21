package com.kenshi.data.model.video

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoVideoSearchDocument(
    @SerialName("author")
    val author: String,
    @SerialName("datetime")
    val datetime: LocalDate,
    @SerialName("play_time")
    val playTime: Int,
    @SerialName("thumbnail")
    val thumbnailUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String
)
