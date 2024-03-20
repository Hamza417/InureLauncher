package app.simple.inurelauncher.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import app.simple.inurelauncher.ui.composables.Apps
import app.simple.inurelauncher.ui.theme.InureLauncherTheme
import app.simple.inurelauncher.viewmodels.AppLoaderViewModel

class MainActivity : ComponentActivity() {

    private val appLoaderViewModel by viewModels<AppLoaderViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        basicLauncherSetup()
        setContent {
            val bottomSheetOffset = remember { mutableFloatStateOf(0f) }
            val bottomSheetVisible = remember { mutableStateOf(false) }
            val animatedOffset = animateFloatAsState(targetValue = if (bottomSheetVisible.value) 0f else 500f, label = "")

            InureLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = androidx.compose.ui.graphics.Color.Transparent) {
                    BottomSheetScaffold(
                            modifier = Modifier.fillMaxSize(),
                            containerColor = androidx.compose.ui.graphics.Color.Transparent,
                            sheetShadowElevation = 24.dp,
                            sheetTonalElevation = 0.dp,
                            sheetPeekHeight = 72.dp,
                            sheetContent = {
                                Apps(context = this@MainActivity, apps = appLoaderViewModel.getApps().collectAsState().value)
                            },
                            content = {
                                Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(androidx.compose.ui.graphics.Color.Transparent),
                                        verticalArrangement = Arrangement.Bottom
                                ) {
                                    Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(72.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = androidx.compose.ui.Alignment.Bottom
                                    ) {
                                        // Replace this with your actual list of apps
                                        val apps = listOf("App1", "App2", "App3", "App4")

                                        apps.forEach { appName ->
                                            Card(
                                                    modifier = Modifier
                                                        .width(72.dp)
                                                        .height(72.dp)
                                            ) {
                                                // Replace this with your actual app icon
                                                Text(text = appName)
                                            }
                                        }
                                    }
                                }
                            },
                    )
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
