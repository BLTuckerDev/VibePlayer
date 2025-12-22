package dev.bltucker.vibeplayer.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bltucker.vibeplayer.common.theme.VibePlayerTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    strokeWidth: Dp = 4.dp
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = MaterialTheme.colorScheme.tertiary,
        strokeWidth = strokeWidth,
        strokeCap = StrokeCap.Round
    )
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator(size = 64.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    VibePlayerTheme {
        LoadingIndicator()
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    VibePlayerTheme {
        LoadingScreen(modifier = Modifier.fillMaxSize())
    }
}
