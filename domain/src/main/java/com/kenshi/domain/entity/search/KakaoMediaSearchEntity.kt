package com.kenshi.domain.entity.search

import com.kenshi.domain.model.KakaoMediaSearchInfo

data class KakaoMediaSearchEntity (
    val isEnd: Boolean,
    val itemList: List<KakaoMediaSearchInfo>
)