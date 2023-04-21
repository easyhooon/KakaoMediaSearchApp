package com.kenshi.data.model.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoVideoSearchResponse(
    @SerialName("documents")
    val documents: List<KakaoVideoSearchDocument>,
    @SerialName("meta")
    val meta: KakaoVideoSearchMeta
)



