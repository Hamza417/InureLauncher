package app.simple.inurelauncher.viewmodels

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.LauncherApps
import android.os.UserHandle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.simple.inurelauncher.utils.PackageUtils.getLaunchableApps
import app.simple.inurelauncher.utils.PackageUtils.loadApplicationName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppLoaderViewModel(application: Application) : AndroidViewModel(application) {

    private val launcherAppsService = application.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    private val launcherAppsCallback = object : LauncherApps.Callback() {
        override fun onPackageRemoved(packageName: String, user: UserHandle) {
            loadApps()
        }

        override fun onPackageAdded(packageName: String, user: UserHandle) {
            loadApps()
        }

        override fun onPackageChanged(packageName: String, user: UserHandle) {
            loadApps()
        }

        override fun onPackagesAvailable(packageNames: Array<out String>, user: UserHandle, replacing: Boolean) {
            loadApps()
        }

        override fun onPackagesUnavailable(packageNames: Array<out String>, user: UserHandle, replacing: Boolean) {
            loadApps()
        }

        override fun onPackagesSuspended(packageNames: Array<out String>, user: UserHandle) {
            loadApps()
        }

        override fun onPackagesUnsuspended(packageNames: Array<out String>, user: UserHandle) {
            loadApps()
        }
    }

    init {
        launcherAppsService.registerCallback(launcherAppsCallback)
    }

    private val apps: MutableLiveData<List<ApplicationInfo>> by lazy {
        MutableLiveData<List<ApplicationInfo>>().also {
            loadApps()
        }
    }

    fun getApps(): LiveData<List<ApplicationInfo>> {
        return apps
    }

    private fun loadApps() {
        viewModelScope.launch(Dispatchers.IO) {
            val apps = getApplication<Application>().packageManager.getLaunchableApps()

            apps.forEach {
                it.name = it.loadApplicationName(getApplication())
            }

            this@AppLoaderViewModel.apps.postValue(apps.sortedBy {
                it.name
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        launcherAppsService.unregisterCallback(launcherAppsCallback)
    }
}
