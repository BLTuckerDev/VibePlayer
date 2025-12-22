package dev.bltucker.vibeplayer.home

import dagger.Reusable
import javax.inject.Inject

data class HomeScreenModel(
    val isScanning: Boolean = false,
    val hasScannedAtLeastOnce: Boolean = false,
    val showScannerSettings: Boolean = false,

    val isError: Boolean = false,

    val hasRequiredPermissions: Boolean? = null,
    val trackList: List<Track> = emptyList(),
)

//TODO what is a track
data class Track(val name: String)

@Reusable
class HomeScreenModelReducer @Inject constructor() {
    fun createInitialState() = HomeScreenModel()

    fun updateModelWithHasRequiredPermissions(previousModel: HomeScreenModel, hasRequiredPermissions: Boolean): HomeScreenModel {
        return previousModel.copy(hasRequiredPermissions = hasRequiredPermissions)
    }

    fun updateModelWithTrackList(previousModel: HomeScreenModel, trackList: List<Track>): HomeScreenModel {
        return previousModel.copy(trackList = trackList)
    }

    fun updateModelWithIsScanning(previousModel: HomeScreenModel, isScanning: Boolean): HomeScreenModel {
        return previousModel.copy(isScanning = isScanning, hasScannedAtLeastOnce = true, isError = false)
    }

}