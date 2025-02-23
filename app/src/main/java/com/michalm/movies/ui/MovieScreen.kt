package com.michalm.movies.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.michalm.movies.ui.components.Banner
import com.michalm.movies.ui.components.LoadingIndicator
import com.michalm.movies.ui.details.DetailsScreen
import com.michalm.movies.ui.nowplaying.NowPlayingScreen
import com.michalm.movies.ui.theme.MoviesTheme
import com.michalm.movies.ui.topappbars.TopAppBarDetails
import com.michalm.movies.ui.topappbars.TopAppBarNowPlaying
import com.michalm.movies.utils.NavDestinations
import com.michalm.movies.utils.ResponseState
import com.michalm.movies.utils.TopBarState


@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val topBarState by viewModel.topBarState.collectAsState()
    val responseState by viewModel.responseState.collectAsState()

    MoviesTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                when (topBarState) {
                    TopBarState.NOW_PLAYING -> {
                        TopAppBarNowPlaying(
                            viewModel = viewModel,
                        )
                    }

                    TopBarState.DETAILS -> {
                        TopAppBarDetails(
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }
                }
            }
        ) { innerPadding ->

            Column(modifier = Modifier.padding(innerPadding)) {
                LoadingIndicator(responseState is ResponseState.Loading)

                if (responseState is ResponseState.Error)
                    Banner(text = (responseState as ResponseState.Error).message)

                NavHost(navController, startDestination = NavDestinations.MOVIE_NOW_PLAYING_ROUTE) {
                    composable(NavDestinations.MOVIE_NOW_PLAYING_ROUTE) {
                        viewModel.setTopBarState(TopBarState.NOW_PLAYING)
                        NowPlayingScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    composable(NavDestinations.MOVIE_DETAILS_ROUTE) {
                        viewModel.setTopBarState(TopBarState.DETAILS)
                        DetailsScreen(
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
}