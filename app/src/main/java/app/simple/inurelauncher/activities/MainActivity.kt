package app.simple.inurelauncher.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
            InureLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = androidx.compose.ui.graphics.Color.Transparent) {
                    BottomSheetScaffold(
                            modifier = Modifier.fillMaxSize(),
                            containerColor = androidx.compose.ui.graphics.Color.Transparent,
                            sheetShadowElevation = 24.dp,
                            sheetTonalElevation = 0.dp,
                            // sheetPeekHeight = 72.dp,
                            sheetContent = {
                                Apps(context = this@MainActivity, apps = appLoaderViewModel.getApps().collectAsState().value)
                            },
                            content = {
                                // Your content
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
