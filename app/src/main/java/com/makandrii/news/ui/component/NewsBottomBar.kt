package com.makandrii.news.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.makandrii.news.ui.theme.NewsTheme

@Composable
fun NewsBottomBar(
    tabIndex: Int,
    onTabClicked: (tabIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("Мої новини", "Пошук", "Закладки")

    TabRow(
        selectedTabIndex = tabIndex,
        divider = {},
        indicator = {},
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(
                        text = title,
                        color = if (tabIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                selected = tabIndex == index,
                onClick = { onTabClicked(index) },
                icon = {
                    when (index) {
                        0 -> Icon(imageVector = Icons.Outlined.Newspaper, contentDescription = null)

                        1 -> Icon(imageVector = Icons.Outlined.Search, contentDescription = null)

                        2 -> Icon(imageVector = Icons.Outlined.Bookmarks, contentDescription = null)
                    }
                },
                selectedContentColor = MaterialTheme.colorScheme.tertiary,
                unselectedContentColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
fun NewsBottomBarPreview() {
    NewsTheme(darkTheme = true) {
        NewsBottomBar(
            onTabClicked = {},
            tabIndex = 0
        )
    }
}