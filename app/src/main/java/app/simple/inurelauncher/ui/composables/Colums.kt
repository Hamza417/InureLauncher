package app.simple.inurelauncher.ui.composables

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.simple.inurelauncher.glide.icon.AppIcon
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Apps(context: Context, apps: List<ApplicationInfo>) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 100.dp),
                     modifier = Modifier
                         .fillMaxHeight()
                         .padding(horizontal = 16.dp)) {
        items(apps.size) { index ->
            Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                GlideImage(
                        model = AppIcon(context = context, packageName = apps[index].packageName),
                        contentDescription = "",
                        modifier = Modifier.padding(8.dp),
                )
                Text(
                        text = apps[index].name,
                        maxLines = 2,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        fontSize = 12.sp
                )
            }
        }
    }
}