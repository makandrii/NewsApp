package com.makandrii.news.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.makandrii.news.R
import com.makandrii.news.data.model.News

@Composable
fun Result(
    news: List<News>,
    onCardClicked: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(news) {
            NewsCard(
                news = it,
                onCardClicked = onCardClicked,
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
            )
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}