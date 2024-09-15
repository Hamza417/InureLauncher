package app.simple.inurelauncher.ui.screens

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import app.simple.inurelauncher.glide.icon.AppIcon
import app.simple.inurelauncher.utils.PackageUtils.launchApp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun AppDrawer() {

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppDrawer(context: Context, apps: List<ApplicationInfo>, navController: androidx.navigation.NavController? = null) {
    var statusBarHeight by remember { mutableIntStateOf(0) }
    var navigationBarHeight by remember { mutableIntStateOf(0) }

    statusBarHeight = WindowInsetsCompat.toWindowInsetsCompat(
            LocalView.current.rootWindowInsets).getInsets(WindowInsetsCompat.Type.statusBars()).top
    navigationBarHeight = WindowInsetsCompat.toWindowInsetsCompat(
            LocalView.current.rootWindowInsets).getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

    val statusBarHeightPx = statusBarHeight
    val statusBarHeightDp = with(LocalDensity.current) { statusBarHeightPx.toDp() }
    val navigationBarHeightPx = navigationBarHeight
    val navigationBarHeightDp = with(LocalDensity.current) { navigationBarHeightPx.toDp() }
    val topPadding = 8.dp + statusBarHeightDp
    val bottomPadding = 8.dp + navigationBarHeightDp

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp),
                     modifier = Modifier
                         .fillMaxHeight()
                         .padding(horizontal = 8.dp)
                         .graphicsLayer {
                                clip = false
                         },
                     contentPadding = PaddingValues(
                             top = topPadding,
                             start = 8.dp,
                             end = 8.dp,
                             bottom = bottomPadding)) {
        items(apps.size) { index ->
            Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .graphicsLayer {
                            clip = false
                        }
                        .clickable {
                            apps[index].launchApp(context.packageManager, context)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                Box(
                        modifier = Modifier
                            .graphicsLayer {
                                clip = false
                            },
                        contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                            model = AppIcon(context = context, packageName = apps[index].packageName),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(8.dp)
                                .height(72.dp)
                                .width(72.dp)
                                .blur(4.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                                .graphicsLayer {
                                    clip = false
                                }
                    )
                    GlideImage(
                            model = AppIcon(context = context, packageName = apps[index].packageName),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(8.dp)
                                .height(72.dp)
                                .width(72.dp)
                    )
                }
                Text(
                        text = apps[index].name,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}
