package com.kenshi.data.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoImageSearchMeta(
    @SerialName("is_end")
    val isEnd: Boolean,
    @SerialName("pageable_count")
    val pageableCount: Int,
    @SerialName("total_count")
    val totalCount: Int
)