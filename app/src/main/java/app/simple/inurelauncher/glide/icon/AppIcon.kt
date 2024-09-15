package app.simple.inurelauncher.glide.icon

import android.content.Context

class AppIcon(val context: Context, val packageName: String) {
    override fun hashCode(): Int {
        return packageName.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AppIcon) {
            return false
        }

        return packageName == other.packageName
    }
}
