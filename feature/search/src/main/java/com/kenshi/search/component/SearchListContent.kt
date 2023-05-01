package com.kenshi.search.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kenshi.domain.model.KakaoMediaSearchItem


@Immutable
data class mediaSearchListState(
    val query: String,
    val pageable: Boolean,
    val page: Int,
    val mediaList: List<KakaoMediaSearchItem>
)

@Composable
fun SearchListContent(
    modifier: Modifier = Modifier,
    searchMediaList: mediaSearchListState,
    listState: LazyListState = rememberLazyListState(),
    onNextPage: (page: Int) -> Unit,
    onClickLink: (KakaoMediaSearchItem) -> Unit,
    onClickFavorite: (KakaoMediaSearchItem) -> Unit,
) {
    // val coroutineScope = rememberCoroutineScope()

    val shouldStartPaginate by remember(searchMediaList) {
        derivedStateOf {
            searchMediaList.pageable &&
                    (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) >= searchMediaList.mediaList.size - 1
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate) {
        if (shouldStartPaginate) {
            onNextPage(searchMediaList.page + 1)
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = searchMediaList.mediaList.size,
            key = { index -> index }
        ) { index ->
            val mediaItem = searchMediaList.mediaList[index]

            SearchMediaItem(
                mediaItem = mediaItem,
                onClickLink = onClickLink,
                onClickFavorite = onClickFavorite
            )
        }
    }
}

@Composable
fun SearchMediaItem(
    modifier: Modifier = Modifier,
    mediaItem: KakaoMediaSearchItem,
    onClickLink: (KakaoMediaSearchItem) -> Unit,
    onClickFavorite: (KakaoMediaSearchItem) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clickable {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("")
                )
                startActivity(context, browserIntent, null)
            }
            .height(300.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = mediaItem)
                .crossfade(durationMillis = 1000)
                .placeholder(drawableResId = com.kenshi.ui.R.drawable.ic_placeholder)
                .error(drawableResId = com.kenshi.ui.R.drawable.ic_placeholder)
                .build(),
            contentDescription = stringResource(id = com.kenshi.ui.R.string.search_media),
            contentScale = ContentScale.Crop
        )
    }
}

//@Composable
//@Preview
//fun KakaoMediaItemPreview() {
//    SearchMediaItem(
//        mediaItem = KakaoMediaSearchItem(
//            isFavorite = true,
//            mediaInfo = KakaoMediaSearchInfo(
//                title = "",
//                url = "",
//                thumbnailUrl = "",
//                dateTime = LocalDate().now(),
//                mediaType = KakaoMediaSearchType.IMAGE
//            ),
//            onClickLink = {},
//            onClickFavorite = {}
//    )
//}