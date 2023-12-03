package app.simple.inurelauncher.glide.icon

import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher

class AppIconFetcher internal constructor(private val appIcon: AppIcon) : DataFetcher<Bitmap> {

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in Bitmap>) {
        try {
            val launcher = appIcon.context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
            val activityList = launcher.getActivityList(appIcon.packageName, android.os.Process.myUserHandle())[0]
            val displayMetrics = appIcon.context.resources.displayMetrics

            callback.onDataReady(activityList.getIcon(displayMetrics.densityDpi).toBitmap(150, 150))
        } catch (e: IndexOutOfBoundsException) {
            callback.onLoadFailed(e)
        }
    }

    override fun cleanup() {
        /* no-op */
    }

    override fun cancel() {
        /* no-op */
    }

    override fun getDataClass(): Class<Bitmap> {
        return Bitmap::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.LOCAL
    }
}
