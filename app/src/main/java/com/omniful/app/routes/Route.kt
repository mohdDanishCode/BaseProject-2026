package com.omniful.app.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.omniful.app.presentation.dashboard.DashboardScreen
import com.omniful.app.presentation.login.LoginScreen
import com.omniful.app.presentation.login.LoginViewModel
import com.omniful.app.presentation.notes.NoteEntryScreen
import com.omniful.app.presentation.notes.NotesHomeScreen
import com.omniful.app.presentation.otp.OtpScreen
import com.omniful.app.presentation.splash.SplashScreen
import com.omniful.database.model.NoteEntity

sealed class ScreenRoute(val route: String) {

    object Splash : ScreenRoute("splash")
    object Login : ScreenRoute("login")
    object Otp : ScreenRoute("otp")


    object NotesHome : ScreenRoute("notes_home")


    object NotesEntry : ScreenRoute("notes_entry")


    object Dashboard : ScreenRoute("dashboard")
}

@Composable
fun NavGraph(navController: NavHostController) {

    val appNavigator = LocalAppNavigator.current

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.NotesHome.route
    ) {

        composable(ScreenRoute.NotesHome.route) {
            NotesHomeScreen(
                onAdd = { navController.navigate(ScreenRoute.NotesEntry.route) },
                onNoteClick = {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("note", it)
                    navController.navigate(ScreenRoute.NotesEntry.route)
                }
            )
        }


        composable(ScreenRoute.NotesEntry.route) {
            val note =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<NoteEntity>("note")

            NoteEntryScreen(
                note = note,
                onBack = { navController.popBackStack() }
            )
        }


        composable(ScreenRoute.Login.route) {
            val vm: LoginViewModel = hiltViewModel()

            LoginScreen(
                viewModel = vm,
                onSkip = {
                    appNavigator.navigateByPopUpToScreen(ScreenRoute.Dashboard)
                },
                onGoogleLogin = {},
                onAppleLogin = {},
                onContinueSuccess = {
//                    navController.currentBackStackEntry?.savedStateHandle?.apply {
//                        set(EmailArgument, it.username)
//                    }
//                    navController.currentBackStackEntry?.savedStateHandle?.apply {
//                        set(PasswordArgument, it.password)
//                    }
//                    navController.currentBackStackEntry?.savedStateHandle?.apply {
//                        set(ExpiryTimeArgument, it.expirationTime)
//                    }
                    navController.navigate(ScreenRoute.Otp.route)

                }
            )
        }

        composable(ScreenRoute.Otp.route) {
//            navController.previousBackStackEntry?.savedStateHandle?.get<String>(
//                EmailArgument,
//            )?.let { userName ->
//                navController.previousBackStackEntry?.savedStateHandle?.get<String>(
//                    PasswordArgument,
//                )?.let { password ->
//                    navController.previousBackStackEntry?.savedStateHandle?.get<String>(
//                        ExpiryTimeArgument,
//                    )?.let { expireTime ->
//                        OtpScreen(userName, password, expireTime = expireTime, canNavigate = {
//                            appNavigator.navigateByPopUpToScreen(appNavigator.getNextDestination())
//                        }) {
//                            appNavigator.popBackStack()
//                        }
//                    }
//
//                }
//            }
            OtpScreen("", "", expireTime = "", canNavigate = {
                appNavigator.navigateByPopUpToScreen(ScreenRoute.Dashboard)
//                appNavigator.navigateByPopUpToScreen(appNavigator.getNextDestination())
            }) {
                appNavigator.popBackStack()
            }


        }

        composable(ScreenRoute.Dashboard.route) {
            DashboardScreen()
        }

        composable(ScreenRoute.Splash.route) {
            SplashScreen(){
                appNavigator.navigateByPopUpToScreen(appNavigator.getNextDestination())
            }
        }

    }
}