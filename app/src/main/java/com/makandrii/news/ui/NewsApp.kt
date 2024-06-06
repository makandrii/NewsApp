package com.makandrii.news.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makandrii.news.R
import com.makandrii.news.data.model.News
import com.makandrii.news.ui.component.NewsBottomBar
import com.makandrii.news.ui.screen.BookmarksScreen
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

                    2 -> {
                        BookmarksScreen(
                            bookmarks = uiState.bookmarks,
                            onCardClicked = onCardClicked(newsViewModel, navController)
                        )
                    }
                }

            }
        }
        composable(route = Screens.News.name) {
            val context = LocalContext.current
            if (uiState.selectedNews != null) {
                NewsScreen(
                    news = uiState.selectedNews!!,
                    onBackButtonClicked = {
                        navController.navigateUp()
                    },
                    onBookmarkButtonClicked = {
                        if (uiState.selectedNews!!.isBookmarked) {
                            newsViewModel.removeBookmark(uiState.selectedNews!!)
                        } else {
                            newsViewModel.addBookmark(uiState.selectedNews!!)
                        }
                    },
                    onShareButtonClicked = {
                        shareNews(context, it.title, it.sourceUrl)
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

private fun shareNews(context: Context, title: String, source: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "$source: $title"
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        ContextCompat.startActivity(context, shareIntent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            context,
            context.getString(R.string.sharing_not_available),
            Toast.LENGTH_LONG
        ).show()
    }
}