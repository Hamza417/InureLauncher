package app.simple.inurelauncher.viewmodels

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.simple.inurelauncher.utils.PackageUtils.getInstalledApps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppLoaderViewModel(application: Application) : AndroidViewModel(application) {

    private val appsFlow = MutableStateFlow<List<ApplicationInfo>>(emptyList()).also {
        loadApps()
    }

    fun getApps() = appsFlow.asStateFlow()

    private fun loadApps() {
        viewModelScope.launch(Dispatchers.IO) {
            val apps = getApplication<Application>().packageManager.getInstalledApps()
            appsFlow.emit(apps)
        }
    }
}