package dev.bltucker.vibeplayer.player

import dagger.Reusable
import dev.bltucker.vibeplayer.db.TrackEntity
import javax.inject.Inject

data class PlayerScreenModel(
    val currentTrack: TrackEntity? = null,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentPositionMs: Long = 0,
    val durationMs: Long = 0
) {
    val formattedCurrentPosition: String
        get() = formatTime(currentPositionMs)

    val formattedDuration: String
        get() = formatTime(durationMs)

    val progress: Float
        get() = if (durationMs > 0) currentPositionMs.toFloat() / durationMs.toFloat() else 0f

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}

@Reusable
class PlayerScreenModelReducer @Inject constructor() {

    fun createInitialState() = PlayerScreenModel(isLoading = true)

    fun updateWithTrack(previousModel: PlayerScreenModel, track: TrackEntity?): PlayerScreenModel {
        return previousModel.copy(
            currentTrack = track,
            isLoading = false
        )
    }

    fun updateWithIsPlaying(previousModel: PlayerScreenModel, isPlaying: Boolean): PlayerScreenModel {
        return previousModel.copy(isPlaying = isPlaying)
    }

    fun updateWithProgress(previousModel: PlayerScreenModel, currentPositionMs: Long, durationMs: Long): PlayerScreenModel {
        return previousModel.copy(
            currentPositionMs = currentPositionMs,
            durationMs = durationMs
        )
    }
}
