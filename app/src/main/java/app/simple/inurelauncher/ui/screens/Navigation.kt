package app.simple.inurelauncher.ui.screens

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.simple.inurelauncher.viewmodels.AppLoaderViewModel

@Composable
fun LauncherNavigation(context: Context, apps: List<ApplicationInfo>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(context = context, navController = navController)
        }

        composable(Routes.APP_DRAWER) {
            AppDrawer(context = context, apps = apps, navController = navController)
        }
    }
}
