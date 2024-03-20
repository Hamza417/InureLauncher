package app.simple.inurelauncher.ui.screens

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp),
                     modifier = Modifier
                         .fillMaxHeight()
                         .padding(horizontal = 8.dp)) {
        items(apps.size) { index ->
            Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            apps[index].launchApp(context.packageManager, context)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                GlideImage(
                        model = AppIcon(context = context, packageName = apps[index].packageName),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .height(72.dp)
                            .width(72.dp)
                )
                Text(
                        text = apps[index].name,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        modifier = Modifier.width(64.dp),
                        color = Color.Black
                )
            }
        }
    }
}
