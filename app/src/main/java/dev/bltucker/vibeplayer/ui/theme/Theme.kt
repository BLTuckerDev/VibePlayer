package dev.bltucker.vibeplayer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ButtonPrimary,
    onPrimary = TextPrimary,
    primaryContainer = ButtonPrimary30,
    onPrimaryContainer = TextPrimary,
    secondary = TextSecondary,
    onSecondary = SurfaceBG,
    tertiary = AccentColor,
    onTertiary = SurfaceBG,
    background = SurfaceBG,
    onBackground = TextPrimary,
    surface = SurfaceBG,
    onSurface = TextPrimary,
    surfaceVariant = ButtonHover,
    onSurfaceVariant = TextSecondary,
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun VibePlayerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
