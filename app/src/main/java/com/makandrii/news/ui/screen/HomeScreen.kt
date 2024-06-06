package com.makandrii.news.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.makandrii.news.R
import com.makandrii.news.data.model.News
import com.makandrii.news.ui.Category
import com.makandrii.news.ui.InternetConnectionState
import com.makandrii.news.ui.component.NewsTopAppBar
import com.makandrii.news.ui.component.Result

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    categories: List<Category>,
    onCategoryClicked: (category: String) -> Unit,
    onNavigationButtonClicked: () -> Unit,
    onCardClicked: (News) -> Unit,
    fetchData: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState {
        categories.size
    }

    Scaffold(
        topBar = {
            NewsTopAppBar(
                pagerState = pagerState,
                categories = categories,
                onCategoryClicked = onCategoryClicked,
                onNavigationButtonClicked = onNavigationButtonClicked,
            )
        },
        modifier = modifier
    ) { paddingValues ->
        if (categories[pagerState.currentPage].state == InternetConnectionState.Loading) {
            if (pagerState.currentPage == 0) {
                fetchData(null)
            } else {
                fetchData(categories[pagerState.currentPage].name)
            }
        }

        HorizontalPager(
            state = pagerState,
            contentPadding = paddingValues,
            beyondBoundsPageCount = 0
        ) {
            when (categories[it].state) {
                is InternetConnectionState.Loading -> {
                    Loading(
                        modifier = modifier.fillMaxWidth()
                    )
                }

                is InternetConnectionState.Error -> Error(
                    modifier = modifier.fillMaxWidth()
                )

                is InternetConnectionState.Success -> Result(
                    news = (categories[it].state as InternetConnectionState.Success).news,
                    onCardClicked = onCardClicked,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading_lottie)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
    )
}

@Composable
fun Error(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
    }
}