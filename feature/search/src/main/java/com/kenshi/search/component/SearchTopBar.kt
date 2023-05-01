package com.kenshi.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kenshi.ui.Elevation

@Composable
fun SearchTopBar(
    query: String,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
) {
    SearchWidget(
        query = query,
        onTextChange = onTextChange,
        onSearchClick = onSearchClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWidget(
    query: String,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        tonalElevation = Elevation.Level1,
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = MaterialTheme.colorScheme.onSurfaceVariant.alpha),
                    text = "검색",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.surface
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(alpha = MaterialTheme.colorScheme.onSurfaceVariant.alpha),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = com.kenshi.ui.R.string.search_icon),
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (query.isNotEmpty()) {
                            onTextChange("")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = com.kenshi.ui.R.string.close_icon),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(query)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
@Preview
fun SearchWidgetPreview() {
    SearchWidget(
        query = "",
        onTextChange = {},
        onSearchClick = {}
    )
}