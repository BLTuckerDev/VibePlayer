package dev.bltucker.vibeplayer.common.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bltucker.vibeplayer.R
import dev.bltucker.vibeplayer.common.theme.VibePlayerTheme

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp
) {
    Icon(
        painter = painterResource(R.drawable.logo_svg),
        contentDescription = "VibePlayer Logo",
        modifier = modifier.size(size),
        tint = MaterialTheme.colorScheme.tertiary
    )
}

@Preview
@Composable
private fun LogoPreview() {
    VibePlayerTheme {
        Logo()
    }
}
