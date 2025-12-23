package dev.bltucker.vibeplayer.common.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dev.bltucker.vibeplayer.db.TrackEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@ViewModelScoped
class VibePlayer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val reducer: PlayerReducer
) {

    private val mutablePlayerModel = MutableStateFlow(reducer.createInitialModel())
    val obsevablePlayerModel: StateFlow<PlayerModel> = mutablePlayerModel.asStateFlow()

    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                val state = when (playbackState) {
                    Player.STATE_IDLE -> PlaybackState.IDLE
                    Player.STATE_BUFFERING -> PlaybackState.BUFFERING
                    Player.STATE_READY -> PlaybackState.READY
                    Player.STATE_ENDED -> PlaybackState.ENDED
                    else -> PlaybackState.IDLE
                }
                mutablePlayerModel.update { reducer.updateModelWithPlaybackState(it, state) }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                mutablePlayerModel.update { reducer.updateModelWithIsPlaying(it, isPlaying) }
            }
        })
    }

    fun playTrack(track: TrackEntity) {
        mutablePlayerModel.update { reducer.updateModelWithCurrentTrack(it, track) }
        val mediaItem = MediaItem.fromUri(track.contentUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun play() {
        if (exoPlayer.playbackState == Player.STATE_IDLE) {
            exoPlayer.prepare()
        }
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun skipForward() {
        exoPlayer.seekTo(exoPlayer.currentPosition + SKIP_DURATION_MS)
    }

    fun skipBackward() {
        exoPlayer.seekTo(exoPlayer.currentPosition - SKIP_DURATION_MS)
    }

    fun release() {
        exoPlayer.release()
    }

    companion object {
        private const val SKIP_DURATION_MS = 10_000L
    }
}
