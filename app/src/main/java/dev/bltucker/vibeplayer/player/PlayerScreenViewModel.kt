package dev.bltucker.vibeplayer.player

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bltucker.vibeplayer.common.player.VibePlayer
import dev.bltucker.vibeplayer.db.TrackEntity
import dev.bltucker.vibeplayer.home.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    private val modelReducer: PlayerScreenModelReducer,
    private val vibePlayer: VibePlayer,
    private val tracksRepository: TracksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val trackId: String? = savedStateHandle.get<String>(TRACK_ID_ARG)

    @VisibleForTesting
    val mutableModel = MutableStateFlow(modelReducer.createInitialState())
    val observableModel: StateFlow<PlayerScreenModel> = mutableModel

    private var hasStarted = false

    fun onStart() {
        if (hasStarted) {
            return
        }
        hasStarted = true

        viewModelScope.launch {
            vibePlayer.obsevablePlayerModel.collectLatest { playerModel ->
                mutableModel.update {
                    modelReducer.updateWithTrack(it, playerModel.currentTrack)
                }
                mutableModel.update {
                    modelReducer.updateWithIsPlaying(it, playerModel.isPlaying)
                }
            }
        }

        trackId?.let { id ->
            viewModelScope.launch {
                tracksRepository.getTrackById(id)?.let { track ->
                    vibePlayer.playTrack(track)
                }
            }
        }
    }

    fun onPlayPauseClick() {
        if (mutableModel.value.isPlaying) {
            vibePlayer.pause()
        } else {
            vibePlayer.play()
        }
    }

    fun onPreviousClick() {
        vibePlayer.skipBackward()
    }

    fun onNextClick() {
        vibePlayer.skipForward()
    }

    override fun onCleared() {
        super.onCleared()
        vibePlayer.release()
    }
}
