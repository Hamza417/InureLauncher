package app.simple.inurelauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.simple.inurelauncher.ui.composables.Grid
import app.simple.inurelauncher.ui.theme.InureLauncherTheme
import app.simple.inurelauncher.utils.PackageUtils.getInstalledApps
import app.simple.inurelauncher.utils.PackageUtils.getLaunchableApps
import app.simple.inurelauncher.viewmodels.AppLoaderViewModel

class MainActivity : ComponentActivity() {

    private val appsLoaderViewModel: AppLoaderViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InureLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Grid(context = this@MainActivity, apps = baseContext.packageManager.getLaunchableApps())
                }
            }
        }
    }
}
