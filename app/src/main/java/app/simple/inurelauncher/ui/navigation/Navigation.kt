package app.simple.inurelauncher.ui.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.simple.inurelauncher.ui.screens.AppDrawer
import app.simple.inurelauncher.ui.screens.HomeScreen

private const val ANIMATION_DURATION = 400
private const val DELAY = 100

@Composable
fun LauncherNavigation(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composableWithTransitions(Routes.HOME_SCREEN) {
            HomeScreen(navController = navController)
        }

        composableWithTransitions(Routes.APP_DRAWER) {
            AppDrawer(navController = navController)
        }
    }
}

fun NavGraphBuilder.composableWithTransitions(
        route: String,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
            route = route,
            enterTransition = { scaleIntoContainer() },
            exitTransition = { scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS) },
            popEnterTransition = { scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS) },
            popExitTransition = { scaleOutOfContainer() },
            content = content
    )
}

fun scaleIntoContainer(
        direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
        initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.5f else 1.5f
): EnterTransition {
    return scaleIn(
            animationSpec = tween(ANIMATION_DURATION, delayMillis = DELAY),
            initialScale = initialScale
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION, delayMillis = DELAY))
}

fun scaleOutOfContainer(
        direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
        targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.5f else 1.5f
): ExitTransition {
    return scaleOut(
            animationSpec = tween(
                    durationMillis = ANIMATION_DURATION,
                    delayMillis = DELAY
            ), targetScale = targetScale
    ) + fadeOut(tween(delayMillis = DELAY))
}

enum class ScaleTransitionDirection {
    INWARDS,
    OUTWARDS
}
