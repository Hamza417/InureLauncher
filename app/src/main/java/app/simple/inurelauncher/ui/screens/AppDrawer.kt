package app.simple.inurelauncher.ui.screens

import android.content.pm.ApplicationInfo
import androidx.compose.MutableState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.simple.inurelauncher.glide.icon.AppIcon
import app.simple.inurelauncher.utils.PackageUtils.launchApp
import app.simple.inurelauncher.viewmodels.AppLoaderViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppDrawer(navController: NavController? = null) {
    val context = LocalContext.current
    var apps = remember { listOf<ApplicationInfo>() }
    val appsViewModel: AppLoaderViewModel = viewModel()
    var statusBarHeight by remember { mutableIntStateOf(0) }
    var navigationBarHeight by remember { mutableIntStateOf(0) }

    appsViewModel.getApps().observeAsState().value?.let {
        apps = it
    }

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

    val lazyGridState = rememberLazyGridState()

    val threshold = 200f // Define the threshold value

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            private var totalOffsetY = 0f

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (lazyGridState.firstVisibleItemIndex == 0 && lazyGridState.firstVisibleItemScrollOffset == 0) {
                    totalOffsetY += available.y
                    if (totalOffsetY > 0) {
                        // Adjust the transparency or position of the previous screen based on totalOffsetY
                        // For example, you can use a Composable with a Modifier.alpha() to change the transparency
                        // of the previous screen based on totalOffsetY
                        val alpha = 1f - (totalOffsetY / threshold).coerceIn(0f, 1f)
                        Modifier.alpha(alpha)
                    }
                    if (totalOffsetY > threshold) { // Use the defined threshold for navigation
                        navController?.popBackStack()
                    }
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (lazyGridState.firstVisibleItemIndex == 0 && lazyGridState.firstVisibleItemScrollOffset == 0) {
                    if (available.y > 0) {
                        navController?.popBackStack()
                    }
                }
                return super.onPreFling(available)
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
    ) {

    }

    LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            state = lazyGridState,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
            contentPadding = PaddingValues(
                    top = topPadding,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = bottomPadding
            )
    ) {
        items(apps.size) { index ->
            Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .combinedClickable(
                                onClick = {
                                    apps[index].launchApp(context.packageManager, context)
                                },
                                onLongClick = {

                                }
                        ),
                    colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                            model = AppIcon(context = context, packageName = apps[index].packageName),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(8.dp)
                                .height(72.dp)
                                .width(72.dp)
                                .blur(6.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                        color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}
