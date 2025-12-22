package dev.bltucker.vibeplayer.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.bltucker.vibeplayer.R
import dev.bltucker.vibeplayer.common.theme.VibePlayerTheme

@Composable
fun SongImage(
    albumArtUri: String?,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    cornerRadius: Dp = 8.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(cornerRadius))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (albumArtUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(albumArtUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Album Art",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Default Album Art",
                modifier = Modifier.size(size * 0.6f),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Preview
@Composable
private fun SongImageWithArtPreview() {
    VibePlayerTheme {
        SongImage(
            albumArtUri = "https://example.com/album-art.jpg"
        )
    }
}

@Preview
@Composable
private fun SongImageDefaultPreview() {
    VibePlayerTheme {
        SongImage(
            albumArtUri = null
        )
    }
}
