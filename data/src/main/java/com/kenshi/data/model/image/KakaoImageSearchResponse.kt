package com.kenshi.data.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoImageSearchResponse(
    @SerialName("documents")
    val documents: List<KakaoImageSearchDocument>,
    @SerialName("meta")
    val meta: KakaoImageSearchMeta
)




