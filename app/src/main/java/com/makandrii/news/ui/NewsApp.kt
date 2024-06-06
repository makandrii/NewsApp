package com.makandrii.news.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makandrii.news.data.model.News
import com.makandrii.news.ui.component.NewsBottomBar
import com.makandrii.news.ui.screen.HomeScreen
import com.makandrii.news.ui.screen.NewsScreen
import com.makandrii.news.ui.screen.SearchScreen

enum class Screens {
    Home,
    News
}

@Composable
fun NewsApp(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val uiState by newsViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.name,
        modifier = modifier
    ) {
        composable(route = Screens.Home.name) {
            Scaffold(
                bottomBar = {
                    NewsBottomBar(
                        tabIndex = uiState.tabIndex,
                        onTabClicked = { newsViewModel.setTabIndex(it) })
                }
            ) { paddingValues ->
                when (uiState.tabIndex) {
                    0 -> {
                        HomeScreen(
                            categories = uiState.categories,
                            onCardClicked = onCardClicked(newsViewModel, navController),
                            onCategoryClicked = {
                                newsViewModel.getNews(it)
                            },
                            onNavigationButtonClicked = {
                                newsViewModel.getNews(null)
                            },
                            fetchData = { newsViewModel.getNews(it) },
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                    }

                    1 -> {
                        SearchScreen(
                            searchedNews = uiState.searchedNews,
                            onActionButtonClicked = { newsViewModel.searchNews(it) },
                            onCardClicked = onCardClicked(newsViewModel, navController),
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }

            }
        }
        composable(route = Screens.News.name) {
            if (uiState.selectedNews != null) {
                NewsScreen(
                    news = uiState.selectedNews!!,
                    onBackButtonClicked = {
                        navController.navigateUp()
                    }
                )
            } else {
                navController.navigate(Screens.Home.name)
            }
        }
    }
}

private fun onCardClicked(
    newsViewModel: NewsViewModel,
    navController: NavHostController
): (News) -> Unit = {
    newsViewModel.selectNews(it)
    navController.navigate(Screens.News.name)
}