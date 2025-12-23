package dev.bltucker.vibeplayer.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: String,
    val name: String,
    val artist: String,
    val duration: String,
    val contentUri: String,
    val albumArtUri: String?
)
