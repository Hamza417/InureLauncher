package app.simple.inurelauncher.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import app.simple.inurelauncher.ui.screens.LauncherNavigation
import app.simple.inurelauncher.ui.theme.InureLauncherTheme
import app.simple.inurelauncher.viewmodels.AppLoaderViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : ComponentActivity() {

    private val appLoaderViewModel by viewModels<AppLoaderViewModel>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        basicLauncherSetup()
        setContent {
            InureLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = androidx.compose.ui.graphics.Color.Transparent) {
                    LauncherNavigation(context = this@MainActivity, apps = appLoaderViewModel.getApps().collectAsState().value)
                }
            }
        }
    }

    private fun basicLauncherSetup() {
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.statusBarColor = Color.TRANSPARENT
    }
}
