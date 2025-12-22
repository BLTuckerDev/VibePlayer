package dev.bltucker.vibeplayer.home

import dagger.Reusable
import javax.inject.Inject

data class HomeScreenModel(
    val isScanning: Boolean = false,
    val hasScannedAtLeastOnce: Boolean = false,
    val showScannerSettings: Boolean = false,
    val ignoreDurationSecondsSetting: Long = 30_000,
    val ignoreSizeBytesSetting: Long = 100_000,

    val isError: Boolean = false,

    val hasRequiredPermissions: Boolean? = null,
    val needsToNavigateToPermissions: Boolean = false,
    val trackList: List<Track> = emptyList(),
)

data class Track(
    val id: String,
    val name: String,
    val artist: String,
    val duration: String,
    val albumArtUri: String? = null
)

@Reusable
class HomeScreenModelReducer @Inject constructor() {
    fun createInitialState() = HomeScreenModel()

    fun updateModelWithIgnoreSizeBytesSetting(previousModel: HomeScreenModel, ignoreSizeBytesSetting: Long): HomeScreenModel {
        return previousModel.copy(ignoreSizeBytesSetting = ignoreSizeBytesSetting)
    }

    fun updateModelWithIgnoreDurationSecondsSetting(previousModel: HomeScreenModel, ignoreDurationSecondsSetting: Long): HomeScreenModel {
        return previousModel.copy(ignoreDurationSecondsSetting = ignoreDurationSecondsSetting)
    }

    fun updateModelWithIsError(previousModel: HomeScreenModel, isError: Boolean): HomeScreenModel {
        return previousModel.copy(isError = isError)
    }

    fun updateModelWithShowScannerSettings(previousModel: HomeScreenModel, showScannerSettings: Boolean): HomeScreenModel {
        return previousModel.copy(showScannerSettings = showScannerSettings)
    }

    fun updateModelWithHasRequiredPermissions(previousModel: HomeScreenModel, hasRequiredPermissions: Boolean): HomeScreenModel {
        return previousModel.copy(
            hasRequiredPermissions = hasRequiredPermissions,
            needsToNavigateToPermissions = !hasRequiredPermissions
        )
    }

    fun clearNavigationToPermissions(previousModel: HomeScreenModel): HomeScreenModel {
        return previousModel.copy(needsToNavigateToPermissions = false)
    }

    fun updateModelWithTrackList(previousModel: HomeScreenModel, trackList: List<Track>): HomeScreenModel {
        return previousModel.copy(trackList = trackList)
    }

    fun updateModelWithIsScanning(previousModel: HomeScreenModel, isScanning: Boolean): HomeScreenModel {
        return previousModel.copy(
            isScanning = isScanning,
            hasScannedAtLeastOnce = true,
            showScannerSettings = false,
            isError = false
        )
    }

}