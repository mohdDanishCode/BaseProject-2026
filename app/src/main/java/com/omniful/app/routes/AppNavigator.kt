package com.omniful.app.routes
import android.app.Activity
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

class AppNavigator(
    private val navController: NavController,
    private val activity: Activity,

    ) {


//    val isLogin = appFlowManager.isLogin

    private var _previousRoute: String? = null
    val previousRoute: String?
        get() = _previousRoute

    private var _currentRoute: String? = null
    val currentRoute: String?
        get() = _currentRoute

    fun updateRoutes(newRoute: String?) {
        _previousRoute = _currentRoute
        _currentRoute = newRoute
    }




    fun getNavController() = navController


    fun navigateToScreen(
        screen: ScreenRoute
    ) {
        safeNavigate(screen.route){
            navController.navigate(it)
        }
    }

    fun navigateByPopUpToScreen(
        screen: ScreenRoute,
        isSaveState:Boolean=false
    ) {
        safeNavigate(screen.route){
            navController.navigate(screen.route) {
                popUpTo(0) {
                    saveState = isSaveState
                    inclusive = true
                }
                restoreState = isSaveState
                launchSingleTop = isSaveState

            }
        }
    }

    fun popBackStack() {
        if(!navController.popBackStack()){
            activity.finish()
        }
    }



    private fun safeNavigate(route: String,onNavigate:((String)->Unit)?=null) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        if (currentRoute != route) {
            if (onNavigate != null) {
                onNavigate(route)
            }

        }
    }

}

val LocalAppNavigator: ProvidableCompositionLocal<AppNavigator> =
    compositionLocalOf { error("No AppNavigator found!") }
