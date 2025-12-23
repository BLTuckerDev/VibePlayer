package dev.bltucker.vibeplayer.home

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.bltucker.vibeplayer.db.TrackEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackValidator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tracksRepository: TracksRepository
) {

    suspend fun execute() {
        val totalTracks = tracksRepository.getTrackCount()
        val batchSize = 100
        var processed = 0

        while (processed < totalTracks) {
            val batch = tracksRepository.getTracksBatch(limit = batchSize, offset = processed)
            if (batch.isEmpty()) break

            batch.forEach { track ->
                if (!isValidTrack(track)) {
                    tracksRepository.deleteTrack(track)
                }
            }
            processed += batch.size
        }
    }

    private fun isValidTrack(track: TrackEntity): Boolean {
        try {
            val uri = Uri.parse(track.contentUri)
            val pfd = context.contentResolver.openFileDescriptor(uri, "r")
            if (pfd != null) {
                pfd.close()
                return true
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }
}
