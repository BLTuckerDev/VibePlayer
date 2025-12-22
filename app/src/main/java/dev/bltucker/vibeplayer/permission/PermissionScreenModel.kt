package dev.bltucker.vibeplayer.permission

import dagger.Reusable
import javax.inject.Inject

data class PermissionScreenModel(
    val hasPermission: Boolean = false,
    val permissionDenied: Boolean = false
)

@Reusable
class PermissionScreenModelReducer @Inject constructor() {
    fun createInitialState() = PermissionScreenModel()

    fun updateWithPermissionStatus(
        previousModel: PermissionScreenModel,
        hasPermission: Boolean
    ): PermissionScreenModel {
        return previousModel.copy(hasPermission = hasPermission)
    }

    fun updateWithPermissionDenied(
        previousModel: PermissionScreenModel,
        denied: Boolean
    ): PermissionScreenModel {
        return previousModel.copy(permissionDenied = denied)
    }
}
