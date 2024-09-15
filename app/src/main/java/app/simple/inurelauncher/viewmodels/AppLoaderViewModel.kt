package app.simple.inurelauncher.viewmodels

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.simple.inurelauncher.utils.PackageUtils.getLaunchableApps
import app.simple.inurelauncher.utils.PackageUtils.loadApplicationName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppLoaderViewModel(application: Application) : AndroidViewModel(application) {

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
}
