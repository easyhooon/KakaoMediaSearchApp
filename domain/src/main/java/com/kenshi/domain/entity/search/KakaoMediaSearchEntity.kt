package com.kenshi.domain.entity.search

import com.kenshi.domain.model.KakaoMediaSearchModel

data class KakaoMediaSearchEntity (
    val isEnd: Boolean,
    val itemList: List<KakaoMediaSearchModel>
)