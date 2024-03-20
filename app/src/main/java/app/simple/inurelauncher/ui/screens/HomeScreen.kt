package app.simple.inurelauncher.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
            Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                DockItem(context = context, packageName = "app.simple.inure", modifier = Modifier.weight(1f))
                DockItem(context = context, packageName = "app.simple.peri", modifier = Modifier.weight(1f))
                DockItem(context = context, packageName = "app.simple.positional", modifier = Modifier.weight(1f))
                AppDrawerButton(modifier = Modifier
                    .weight(1F)
                    .clickable {
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
            modifier = modifier
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
        GlideImage(
                model = AppIcon(context, packageName),
                contentDescription = "",
                modifier = Modifier
                    .height(64.dp)
                    .width(64.dp),
                alignment = Alignment.Center
        )
    }
}

@Composable
fun AppDrawerButton(modifier: Modifier = Modifier) {
    Image(painter = painterResource(id = R.drawable.ic_shape_line),
          contentDescription = "",
          modifier = modifier
              .size(64.dp)
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(context = androidx.compose.ui.platform.LocalContext.current, rememberNavController())
}
