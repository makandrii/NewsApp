package com.makandrii.news.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makandrii.news.R
import com.makandrii.news.ui.Category
import com.makandrii.news.ui.theme.NewsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsTopAppBar(
    pagerState: PagerState,
    categories: List<Category>,
    onCategoryClicked: (String) -> Unit,
    onNavigationButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            val scope = rememberCoroutineScope()

            PrimaryScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp,
                divider = {}
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(0) }
                        onNavigationButtonClicked()
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        )
                    )
                }
                categories.drop(1).forEachIndexed { index, category ->
                    Tab(
                        selected = pagerState.currentPage == index + 1,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index + 1) }
                            onCategoryClicked(category.name)
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Text(
                            text = category.uiDisplayName,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(
                                bottom = dimensionResource(id = R.dimen.padding_small)
                            )
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun NewsTopAppBarPreview() {
    NewsTheme {
        NewsTopAppBar(
            pagerState = rememberPagerState {
                0
            },
            categories = listOf(Category("test", "test"), Category("test", "test")),
            onCategoryClicked = {},
            onNavigationButtonClicked = {})
    }
}