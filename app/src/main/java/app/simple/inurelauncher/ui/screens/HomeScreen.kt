package app.simple.inurelauncher.ui.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
fun HomeScreen(context: Context, navController: NavController? = null) {
    Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.ui.graphics.Color.Transparent
    ) {
        Card(
                modifier = Modifier
                    .padding(24.dp)
                    .wrapContentHeight(align = androidx.compose.ui.Alignment.Bottom),
        ) {
            // Dock
            Row {
                DockItem(context = context, packageName = "app.simple.inure")
                DockItem(context = context, packageName = "app.simple.peri")
                DockItem(context = context, packageName = "app.simple.positional")
                AppDrawerButton(modifier = Modifier.clickable {
                    navController?.navigate(Routes.APP_DRAWER)
                })
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DockItem(context: Context, packageName: String, modifier: Modifier = Modifier) {
    // Dock item
    Column(
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    val applicationInfo = context.packageManager.getApplicationInfo(packageName, 0)
                    context.packageManager
                        .getLaunchIntentForPackage(applicationInfo.packageName)
                        ?.let {
                            context.startActivity(it)
                        }
                },
    ) {
        GlideImage(model = AppIcon(context, packageName),
                   contentDescription = "",
                   modifier = Modifier
                       .height(64.dp)
                       .width(64.dp))
    }
}

@Composable
fun AppDrawerButton(modifier: Modifier = Modifier) {
    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_shape_line),
         contentDescription = "",
         modifier.height(64.dp).width(64.dp).fillMaxHeight())
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(context = androidx.compose.ui.platform.LocalContext.current, rememberNavController())
}