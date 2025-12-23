package dev.bltucker.vibeplayer.home

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dev.bltucker.vibeplayer.db.TrackEntity
import dev.bltucker.vibeplayer.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class MediaScanner @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val tracksRepository: TracksRepository
) {

    suspend fun scanForAudio(minDurationMillis: Long, minSizeBytes: Long): Result<Boolean> = withContext(ioDispatcher) {
        runCatching {
            val tracks = mutableListOf<TrackEntity>()

            val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM_ID
            )

            // Filter for music files only
            val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

            val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

            context.contentResolver.query(
                collection,
                projection,
                selection,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val duration = cursor.getLong(durationColumn)
                    val size = cursor.getLong(sizeColumn)
                    val albumId = cursor.getLong(albumIdColumn)

                    if (duration >= minDurationMillis && size >= minSizeBytes) {
                        val contentUri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        val albumArtUri = ContentUris.withAppendedId(
                            Uri.parse("content://media/external/audio/albumart"),
                            albumId
                        )

                        tracks.add(
                            TrackEntity(
                                id = id.toString(),
                                name = title,
                                artist = artist,
                                duration = formatDuration(duration),
                                contentUri = contentUri.toString(),
                                albumArtUri = albumArtUri.toString()
                            )
                        )
                    }
                }
            }

            tracksRepository.addTracks(tracks)
            true
        }
    }

    private fun formatDuration(durationMillis: Long): String {
        val minutes = (durationMillis / 1000) / 60
        val seconds = (durationMillis / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
