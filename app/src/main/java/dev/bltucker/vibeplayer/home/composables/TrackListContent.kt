package dev.bltucker.vibeplayer.home.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.bltucker.vibeplayer.R
import dev.bltucker.vibeplayer.common.theme.AccentColor
import dev.bltucker.vibeplayer.common.theme.SurfaceBG
import dev.bltucker.vibeplayer.common.theme.TextPrimary
import dev.bltucker.vibeplayer.common.theme.TextSecondary
import dev.bltucker.vibeplayer.common.theme.VibePlayerTheme
import dev.bltucker.vibeplayer.home.Track

@Composable
fun TrackListContent(
    modifier: Modifier = Modifier,
    tracks: List<Track>,
    onTrackClick: (Track) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(tracks, key = { it.id }) { track ->
            TrackItem(
                track = track,
                onClick = { onTrackClick(track) }
            )
        }
    }
}

@Composable
private fun TrackItem(
    track: Track,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (track.albumArtUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(track.albumArtUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Album art for ${track.name}",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.default_album_art),
                placeholder = painterResource(R.drawable.default_album_art)
            )
        } else {
            DefaultAlbumArtIcon(modifier = Modifier.size(56.dp))
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = track.name,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
            Text(
                text = track.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }

        Text(
            text = track.duration,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}

@Composable
private fun DefaultAlbumArtIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceBG),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(32.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw circle
            drawCircle(
                color = AccentColor,
                radius = canvasWidth / 2,
                center = Offset(canvasWidth / 2, canvasHeight / 2)
            )

            // Draw music note
            val noteColor = SurfaceBG
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            // Note stem
            val stemPath = Path().apply {
                moveTo(centerX + canvasWidth * 0.15f, centerY - canvasHeight * 0.25f)
                lineTo(centerX + canvasWidth * 0.15f, centerY + canvasHeight * 0.15f)
            }

            drawPath(
                path = stemPath,
                color = noteColor,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
            )

            // Note head (circle)
            drawCircle(
                color = noteColor,
                radius = canvasWidth * 0.12f,
                center = Offset(centerX, centerY + canvasHeight * 0.2f)
            )

            // Flag
            val flagPath = Path().apply {
                moveTo(centerX + canvasWidth * 0.15f, centerY - canvasHeight * 0.25f)
                cubicTo(
                    centerX + canvasWidth * 0.3f, centerY - canvasHeight * 0.2f,
                    centerX + canvasWidth * 0.3f, centerY - canvasHeight * 0.05f,
                    centerX + canvasWidth * 0.15f, centerY
                )
            }

            drawPath(
                path = flagPath,
                color = noteColor
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A131D)
@Composable
private fun TrackListContentPreview() {
    val sampleTracks = listOf(
        Track(
            id = "1",
            name = "505",
            artist = "Arctic Monkeys",
            duration = "4:14",
            albumArtUri = null
        ),
        Track(
            id = "2",
            name = "505",
            artist = "Arctic Monkeys",
            duration = "4:14",
            albumArtUri = null
        ),
        Track(
            id = "3",
            name = "505",
            artist = "Arctic Monkeys",
            duration = "4:14",
            albumArtUri = null
        ),
        Track(
            id = "4",
            name = "505",
            artist = "Arctic Monkeys",
            duration = "4:14",
            albumArtUri = null
        ),
        Track(
            id = "5",
            name = "505",
            artist = "Arctic Monkeys",
            duration = "4:14",
            albumArtUri = null
        )
    )

    VibePlayerTheme {
        TrackListContent(tracks = sampleTracks)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A131D)
@Composable
private fun TrackItemPreview() {
    VibePlayerTheme {
        TrackItem(
            track = Track(
                id = "1",
                name = "505",
                artist = "Arctic Monkeys",
                duration = "4:14",
                albumArtUri = null
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultAlbumArtIconPreview() {
    VibePlayerTheme {
        DefaultAlbumArtIcon(modifier = Modifier.size(56.dp))
    }
}
