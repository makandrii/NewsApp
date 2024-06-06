package com.makandrii.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import com.makandrii.news.ui.NewsApp
import com.makandrii.news.ui.theme.NewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme(dynamicColor = false) {
                NewsApp(
                    modifier = Modifier
                        .statusBarsPadding()
                )
            }
        }
    }
}