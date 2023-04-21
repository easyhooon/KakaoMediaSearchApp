package com.kenshi.data.model.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoVideoSearchMeta(
    @SerialName("is_end")
    val isEnd: Boolean,
    @SerialName("pageable_count")
    val pageableCount: Int,
    @SerialName("total_count")
    val totalCount: Int
)
