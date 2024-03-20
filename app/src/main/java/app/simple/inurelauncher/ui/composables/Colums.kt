package app.simple.inurelauncher.ui.composables

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.simple.inurelauncher.glide.icon.AppIcon
import app.simple.inurelauncher.utils.PackageUtils.launchApp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Apps(context: Context, apps: List<ApplicationInfo>) {
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
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,

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
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        modifier = Modifier.width(64.dp),
                        color = androidx.compose.ui.graphics.Color.Black
                )
            }
        }
    }
}
