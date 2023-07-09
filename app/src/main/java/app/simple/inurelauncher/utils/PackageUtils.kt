package app.simple.inurelauncher.utils

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import android.os.Build

object PackageUtils {
    fun PackageManager.getInstalledApps(flags: Int = PackageManager.GET_META_DATA): List<ApplicationInfo> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getInstalledApplications(PackageManager.ApplicationInfoFlags.of(flags.toLong()))
        } else {
            @Suppress("DEPRECATION")
            getInstalledApplications(flags)
        }
    }

    fun PackageManager.getLaunchableApps(flags: Int = PackageManager.GET_META_DATA): List<ApplicationInfo> {
        return getInstalledApps(flags).filter { app ->
            getLaunchIntentForPackage(app.packageName) != null
        }
    }
}
