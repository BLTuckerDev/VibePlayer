package dev.bltucker.vibeplayer.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bltucker.vibeplayer.R
import dev.bltucker.vibeplayer.common.theme.ButtonPrimary
import dev.bltucker.vibeplayer.common.theme.TextPrimary
import dev.bltucker.vibeplayer.common.theme.TextSecondary
import dev.bltucker.vibeplayer.common.theme.VibePlayerTheme

@Composable
fun ScanSettingsContent(
    modifier: Modifier = Modifier,
    ignoreDurationSeconds: Long = 30_000,
    ignoreSizeBytes: Long = 500_000,
    onDurationSettingChanged: (Long) -> Unit = {},
    onSizeSettingChanged: (Long) -> Unit = {},
    onScanClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.scanning_home_image),
            contentDescription = "Scan settings",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Ignore duration less than",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterOption(
                text = "30s",
                selected = ignoreDurationSeconds == 30_000L,
                onClick = { onDurationSettingChanged(30_000L) },
                modifier = Modifier.weight(1f)
            )
            FilterOption(
                text = "60s",
                selected = ignoreDurationSeconds == 60_000L,
                onClick = { onDurationSettingChanged(60_000L) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ignore size less than",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterOption(
                text = "100KB",
                selected = ignoreSizeBytes == 100_000L,
                onClick = { onSizeSettingChanged(100_000L) },
                modifier = Modifier.weight(1f)
            )
            FilterOption(
                text = "500KB",
                selected = ignoreSizeBytes == 500_000L,
                onClick = { onSizeSettingChanged(500_000L) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onScanClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonPrimary,
                contentColor = TextPrimary
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Scan",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun FilterOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = ButtonPrimary,
                unselectedColor = TextSecondary
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) TextPrimary else TextSecondary,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A131D)
@Composable
private fun ScanSettingsContentPreview() {
    VibePlayerTheme {
        ScanSettingsContent(
            ignoreDurationSeconds = 30_000L,
            ignoreSizeBytes = 500_000L
        )
    }
}
