package dev.bltucker.vibeplayer.common.player

import dev.bltucker.vibeplayer.db.TrackEntity
import javax.inject.Inject

data class PlayerModel(
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val currentTrack: TrackEntity? = null,
    val isPlaying: Boolean = false
)

enum class PlaybackState {
    IDLE,
    BUFFERING,
    READY,
    ENDED
}


class PlayerReducer @Inject constructor() {

    fun createInitialModel(): PlayerModel {
        return PlayerModel()
    }

    fun updateModelWithPlaybackState(model: PlayerModel, playbackState: PlaybackState): PlayerModel {
        return model.copy(playbackState = playbackState)
    }

    fun updateModelWithCurrentTrack(model: PlayerModel, track: TrackEntity?): PlayerModel {
        return model.copy(currentTrack = track)
    }

    fun updateModelWithIsPlaying(model: PlayerModel, isPlaying: Boolean): PlayerModel {
        return model.copy(isPlaying = isPlaying)
    }
}