package app.simple.inurelauncher.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.simple.inurelauncher.R
import app.simple.inurelauncher.glide.icon.AppIcon
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HomeScreen(navController: NavController? = null) {
    // Dock
    Row(
            modifier = Modifier
                .padding(32.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        DockItem(packageName = "app.simple.inure", modifier = Modifier.weight(1f))
        DockItem(packageName = "app.simple.peri", modifier = Modifier.weight(1f))
        DockItem(packageName = "app.simple.positional", modifier = Modifier.weight(1f))
        VerticalDivider(Modifier.height(64.dp))
        AppDrawerButton(modifier = Modifier
            .weight(1F)
            .clickable {
                navController?.navigate(Routes.APP_DRAWER)
            })
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DockItem(packageName: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .clickable {
                    val applicationInfo = context.packageManager.getApplicationInfo(packageName, 0)
                    context.packageManager
                        .getLaunchIntentForPackage(applicationInfo.packageName)
                        ?.let {
                            context.startActivity(it)
                        }
                }
                .padding(8.dp),
    ) {
        GlideImage(
                model = AppIcon(context, packageName),
                contentDescription = "",
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally),
                alignment = Alignment.Center
        )
    }
}

@Composable
fun AppDrawerButton(modifier: Modifier = Modifier) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.padding(8.dp),
    ) {
        Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_drawer),
              contentDescription = "Apps",
              modifier = Modifier.size(64.dp))
    }
}
