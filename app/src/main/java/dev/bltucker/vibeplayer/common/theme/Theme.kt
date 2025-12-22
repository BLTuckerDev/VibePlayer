package dev.bltucker.vibeplayer.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ButtonPrimary,
    onPrimary = TextPrimary,
    primaryContainer = ButtonPrimary,
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
    outline = TextSecondary,
    outlineVariant = ButtonPrimary30,
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
        shapes = AppShapes,
        content = content
    )
}
