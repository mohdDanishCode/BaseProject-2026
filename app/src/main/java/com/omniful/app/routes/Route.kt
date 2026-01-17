package com.omniful.app.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.omniful.app.presentation.movieDetail.MovieDetailScreen
import com.omniful.app.presentation.movieDetail.MovieDetailViewModel
import com.omniful.app.presentation.movies.MovieListScreen

sealed class ScreenRoute(val route: String) {
    object MovieHome : ScreenRoute("movie_home")
    object MovieDetail : ScreenRoute("movie_detail")
}

@Composable
fun NavGraph(navController: NavHostController) {

    val appNavigator = LocalAppNavigator.current

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.MovieHome.route
    ) {


        composable(ScreenRoute.MovieHome.route) {
            MovieListScreen {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("movie-id", it)
                navController.navigate(ScreenRoute.MovieDetail.route)
            }
        }

        composable(ScreenRoute.MovieDetail.route) {

            val movieId =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Long>("movie-id")

            movieId?.let {
                val viewModel: MovieDetailViewModel = hiltViewModel()
                viewModel.loadMovie(movieId)
                MovieDetailScreen(
                    viewModel = hiltViewModel(),
                    onBackClick = { navController.popBackStack() }
                )
            }


        }



    }
}