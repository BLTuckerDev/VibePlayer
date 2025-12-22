package dev.bltucker.vibeplayer.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.bltucker.vibeplayer.R
import dev.bltucker.vibeplayer.common.PermissionChecker
import dev.bltucker.vibeplayer.common.theme.VibePlayerTheme

const val PERMISSION_SCREEN_ROUTE = "permission"

fun NavController.navigateToPermission() {
    navigate(PERMISSION_SCREEN_ROUTE)
}

fun NavGraphBuilder.permissionScreen(
    onPermissionGranted: () -> Unit
) {
    composable(route = PERMISSION_SCREEN_ROUTE) {
        val viewModel = hiltViewModel<PermissionScreenViewModel>()
        val model by viewModel.observableModel.collectAsStateWithLifecycle()

        LifecycleStartEffect(Unit) {
            viewModel.onStart()
            onStopOrDispose { }
        }

        LifecycleResumeEffect(Unit) {
            viewModel.checkPermission()
            onPauseOrDispose { }
        }

        androidx.compose.runtime.LaunchedEffect(model.hasPermission) {
            if (model.hasPermission) {
                onPermissionGranted()
            }
        }

        PermissionScreen(
            modifier = Modifier.fillMaxSize(),
            permissionDenied = model.permissionDenied,
            onPermissionResult = viewModel::onPermissionResult
        )
    }
}

@Composable
private fun PermissionScreen(
    modifier: Modifier = Modifier,
    permissionDenied: Boolean = false,
    onPermissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val permissionChecker = PermissionChecker(context)

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    if (permissionDenied) {
        PermissionDeniedContent(
            modifier = modifier,
            onOpenSettings = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        )
    } else {
        PermissionContent(
            modifier = modifier,
            onAllowAccessClick = {
                permissionLauncher.launch(permissionChecker.getRequiredPermission())
            }
        )
    }
}

@Composable
private fun PermissionContent(
    modifier: Modifier = Modifier,
    onAllowAccessClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_svg),
            contentDescription = "VibePlayer Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "VibePlayer",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "VibePlayer needs access to your music files to build your library and play songs",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onAllowAccessClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Allow Access",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
private fun PermissionDeniedContent(
    modifier: Modifier = Modifier,
    onOpenSettings: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_svg),
            contentDescription = "VibePlayer Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Permission Required",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "VibePlayer needs access to your music files to function properly. Without this permission, the app cannot build your music library or play songs.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onOpenSettings,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Try Again",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionScreenPreview() {
    VibePlayerTheme {
        PermissionContent(
            modifier = Modifier.fillMaxSize(),
            onAllowAccessClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionDeniedScreenPreview() {
    VibePlayerTheme {
        PermissionDeniedContent(
            modifier = Modifier.fillMaxSize(),
            onOpenSettings = {}
        )
    }
}
