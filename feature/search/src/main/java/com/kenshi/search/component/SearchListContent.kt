package com.kenshi.search.component

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kenshi.domain.model.KakaoMediaSearchInfo
import com.kenshi.domain.model.KakaoMediaSearchItem
import com.kenshi.domain.model.KakaoMediaSearchType
import com.kenshi.feature.search.R
import com.kenshi.util.format
import kotlinx.datetime.Clock


@Immutable
data class MediaSearchListState(
    val query: String,
    val pageable: Boolean,
    val page: Int,
    val mediaList: List<KakaoMediaSearchItem>
)

@Composable
fun SearchListContent(
    modifier: Modifier = Modifier,
    mediaSearchList: MediaSearchListState,
    listState: LazyListState = rememberLazyListState(),
    onNextPage: (page: Int) -> Unit,
    onClickLink: (KakaoMediaSearchItem) -> Unit,
    onClickFavorite: (KakaoMediaSearchItem) -> Unit,
) {
    // val coroutineScope = rememberCoroutineScope()

    val shouldStartPaginate by remember(mediaSearchList) {
        derivedStateOf {
            mediaSearchList.pageable &&
                    (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        ?: 0) >= mediaSearchList.mediaList.size - 1
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate) {
        if (shouldStartPaginate) {
            onNextPage(mediaSearchList.page + 1)
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = mediaSearchList.mediaList.size,
            key = { index -> index }
        ) { index ->
            val mediaItem = mediaSearchList.mediaList[index]
            SearchMediaItem(
                modifier = Modifier,
                mediaItem = mediaItem,
                onClickLink = onClickLink,
                onClickFavorite = onClickFavorite
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun SearchMediaItem(
    modifier: Modifier = Modifier,
    mediaItem: KakaoMediaSearchItem,
    onClickLink: (KakaoMediaSearchItem) -> Unit,
    onClickFavorite: (KakaoMediaSearchItem) -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomCenter,
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(mediaItem.mediaInfo.url)
                        )
                        startActivity(context, browserIntent, null)
                    }
                    .height(300.dp)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        data = mediaItem.mediaInfo.originalUrl ?: mediaItem.mediaInfo.thumbnailUrl
                    )
                    .crossfade(durationMillis = 500)
                    .placeholder(drawableResId = com.kenshi.ui.R.drawable.ic_placeholder)
                    .error(drawableResId = com.kenshi.ui.R.drawable.ic_placeholder)
                    .build(),
                contentDescription = stringResource(id = com.kenshi.ui.R.string.search_media),
                contentScale = ContentScale.Crop
            )
            Surface(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .alpha(alpha = MaterialTheme.colorScheme.onSurfaceVariant.alpha),
                color = MaterialTheme.colorScheme.primary
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = mediaItem.mediaInfo.title,
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = mediaItem.mediaInfo.dateTime.format("yyyy-MM-dd HH:mm"),
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        )
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun KakaoMediaItemPreview() {
    SearchMediaItem(
        mediaItem = KakaoMediaSearchItem(
            isFavorite = true,
            mediaInfo = KakaoMediaSearchInfo(
                title = "고양이 보고 가세요",
                url = "",
                thumbnailUrl = "",
                dateTime = Clock.System.now(),
                mediaType = KakaoMediaSearchType.IMAGE
            )
        ),
        onClickLink = {},
        onClickFavorite = {}
    )
}