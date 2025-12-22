package dev.bltucker.vibeplayer.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bltucker.vibeplayer.common.PermissionChecker
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val permissionChecker: PermissionChecker,
    private val modelReducer: HomeScreenModelReducer,
    private val mediaScanner: MediaScanner
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
        
        viewModelScope.launch {
            val currentModel = mutableModel.value
            val result = mediaScanner.scanForAudio(
                minDurationMillis = currentModel.ignoreDurationSecondsSetting,
                minSizeBytes = currentModel.ignoreSizeBytesSetting
            )
            
            result.onSuccess { tracks ->
                mutableModel.update {
                    modelReducer.updateModelWithTrackList(it, tracks)
                }
            }.onFailure {
                mutableModel.update { model ->
                    modelReducer.updateModelWithIsError(model, true)
                }
            }
            
            mutableModel.update {
                modelReducer.updateModelWithIsScanning(it, false)
            }
        }
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
