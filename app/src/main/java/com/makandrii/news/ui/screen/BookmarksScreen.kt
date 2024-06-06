package com.makandrii.news.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.makandrii.news.R
import com.makandrii.news.data.model.News
import com.makandrii.news.ui.component.Result
import com.makandrii.news.ui.theme.NewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    bookmarks: List<News>,
    onCardClicked: (News) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = stringResource(id = R.string.bookmark))
            })
        },
        modifier = modifier
    ) {
        Result(
            news = bookmarks,
            onCardClicked = onCardClicked,
            modifier = Modifier.padding(it)
        )
    }
}

@Preview
@Composable
fun BookmarksScreenPreview() {
    NewsTheme(darkTheme = true) {
        BookmarksScreen(
            bookmarks = emptyList(),
            onCardClicked = {}
        )
    }
}