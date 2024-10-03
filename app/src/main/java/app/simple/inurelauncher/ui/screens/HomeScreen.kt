package app.simple.inurelauncher.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.simple.inurelauncher.glide.icon.AppIcon
import app.simple.inurelauncher.ui.components.AnalogClockComposable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HomeScreen(navController: NavController? = null) {
    // Dock
    Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
        ) {
            AnalogClockComposable(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(24.dp)
            )
        }
        Spacer(modifier = Modifier.size(24.dp).weight(0.4F))
        Row(
                modifier = Modifier
                    .padding(24.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            DockItem(packageName = "app.simple.inure", modifier = Modifier.weight(1f))
            DockItem(packageName = "app.simple.peri", modifier = Modifier.weight(1f))
            DockItem(packageName = "app.simple.positional", modifier = Modifier.weight(1f))
            AppDrawerButton(modifier = Modifier
                .weight(2F)
                .clickable {
                    navController?.navigate(Routes.APP_DRAWER)
                })
        }
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
    Card(
            modifier = modifier.padding(start = 8.dp),
            shape = RoundedCornerShape(64.dp),
            colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
            ),
    ) {
        Icon(
                imageVector = Icons.Rounded.GridView,
                contentDescription = "Apps",
                modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally))
    }
}
