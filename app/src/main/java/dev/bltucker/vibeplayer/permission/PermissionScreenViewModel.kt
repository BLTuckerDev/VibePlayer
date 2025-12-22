package dev.bltucker.vibeplayer.permission

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bltucker.vibeplayer.common.PermissionChecker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PermissionScreenViewModel @Inject constructor(
    private val permissionChecker: PermissionChecker,
    private val modelReducer: PermissionScreenModelReducer
) : ViewModel() {

    @VisibleForTesting
    val mutableModel = MutableStateFlow(modelReducer.createInitialState())
    val observableModel: StateFlow<PermissionScreenModel> = mutableModel

    private var hasStarted = false

    fun onStart() {
        if (hasStarted) {
            return
        }
        hasStarted = true

        checkPermission()
    }

    fun checkPermission() {
        val hasPermission = permissionChecker.hasRequiredMusicPermissions()
        mutableModel.value = modelReducer.updateWithPermissionStatus(
            mutableModel.value,
            hasPermission
        )
    }

    fun onPermissionResult(isGranted: Boolean) {
        mutableModel.value = modelReducer.updateWithPermissionStatus(
            mutableModel.value,
            isGranted
        )
    }
}
