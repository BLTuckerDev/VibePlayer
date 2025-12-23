package dev.bltucker.vibeplayer.home

import dev.bltucker.vibeplayer.db.TrackEntity
import dev.bltucker.vibeplayer.db.TracksDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TracksRepository @Inject constructor(
    private val tracksDao: TracksDao
) {
    fun getTracks(): Flow<List<TrackEntity>> = tracksDao.getAllTracks()

    suspend fun addTracks(tracks: List<TrackEntity>) = tracksDao.insertAll(tracks)

    suspend fun deleteTrack(track: TrackEntity) = tracksDao.delete(track)

    suspend fun getTracksBatch(limit: Int, offset: Int): List<TrackEntity> = tracksDao.getTracksBatch(limit, offset)

    suspend fun getTrackCount(): Int = tracksDao.getTrackCount()

    suspend fun getTrackById(trackId: String): TrackEntity? = tracksDao.getTrackById(trackId)
}
