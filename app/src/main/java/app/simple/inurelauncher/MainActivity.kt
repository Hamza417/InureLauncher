package app.simple.inurelauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import app.simple.inurelauncher.ui.composables.Apps
import app.simple.inurelauncher.ui.theme.InureLauncherTheme
import app.simple.inurelauncher.utils.PackageUtils.getLaunchableApps

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InureLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ModalDrawerSheet {
                        Apps(context = baseContext, apps = packageManager.getLaunchableApps())
                    }
                }
            }
        }
    }
}
