package dev.bltucker.vibeplayer.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bltucker.vibeplayer.common.PermissionChecker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val permissionChecker: PermissionChecker,
    private val modelReducer: HomeScreenModelReducer
) : ViewModel() {

    @VisibleForTesting
    val mutableModel = MutableStateFlow(modelReducer.createInitialState())
    val observableModel: StateFlow<HomeScreenModel> = mutableModel

    fun onResume() {
        checkPermissions()
    }

    fun checkPermissions() {
        val hasPermissions = permissionChecker.hasRequiredMusicPermissions()
        mutableModel.update {
            modelReducer.updateModelWithHasRequiredPermissions(it, hasPermissions)
        }
    }

    fun onClearNavigationToPermissions() {
        mutableModel.update {
            modelReducer.clearNavigationToPermissions(it)
        }
    }

    fun onDurationSettingChanged(durationMillis: Long) {
        mutableModel.update {
            modelReducer.updateModelWithIgnoreDurationSecondsSetting(it, durationMillis)
        }
    }

    fun onSizeSettingChanged(sizeBytes: Long) {
        mutableModel.update {
            modelReducer.updateModelWithIgnoreSizeBytesSetting(it, sizeBytes)
        }
    }

    fun onShowScanSettings() {
        mutableModel.update {
            modelReducer.updateModelWithShowScannerSettings(it, true)
        }
    }

    fun onHideScanSettings() {
        mutableModel.update {
            modelReducer.updateModelWithShowScannerSettings(it, false)
        }
    }

    fun onStartScan() {
        mutableModel.update {
            modelReducer.updateModelWithIsScanning(it, true)
        }
        // TODO: Trigger actual scan operation
    }

    fun onScanAgainClick() {
        mutableModel.update {
            modelReducer.updateModelWithShowScannerSettings(it, true)
        }
    }

    fun onTrackClick(track: Track) {
        // TODO: Navigate to now playing or start playback
    }
}
