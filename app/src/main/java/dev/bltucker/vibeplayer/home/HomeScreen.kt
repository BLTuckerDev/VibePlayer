package dev.bltucker.vibeplayer.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.bltucker.vibeplayer.common.composables.AppTopBar
import dev.bltucker.vibeplayer.common.composables.NavigationTopBar
import dev.bltucker.vibeplayer.home.composables.EmptyInitialContent
import dev.bltucker.vibeplayer.home.composables.EmptyScanResultsContent
import dev.bltucker.vibeplayer.home.composables.ScanSettingsContent
import dev.bltucker.vibeplayer.home.composables.ScanningContent
import dev.bltucker.vibeplayer.home.composables.TrackListContent

const val HOME_SCREEN_ROUTE = "home"

fun NavGraphBuilder.homeScreen(
    onNavigateToPermissions: () -> Unit
) {
    composable(route = HOME_SCREEN_ROUTE) {
        val viewModel = hiltViewModel<HomeScreenViewModel>()
        val model by viewModel.observableModel.collectAsStateWithLifecycle()

        LifecycleResumeEffect(Unit) {
            viewModel.onResume()
            onPauseOrDispose { }
        }

        LaunchedEffect(model.needsToNavigateToPermissions) {
            if (model.needsToNavigateToPermissions) {
                onNavigateToPermissions()
                viewModel.onClearNavigationToPermissions()
            }
        }

        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            model = model,
            onShowScanSettings = viewModel::onShowScanSettings,
            onHideScanSettings = viewModel::onHideScanSettings,
            onStartScan = viewModel::onStartScan,
            onDurationSettingChanged = viewModel::onDurationSettingChanged,
            onSizeSettingChanged = viewModel::onSizeSettingChanged,
            onScanAgainClick = viewModel::onScanAgainClick,
            onTrackClick = viewModel::onTrackClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    model: HomeScreenModel,
    onShowScanSettings: () -> Unit,
    onHideScanSettings: () -> Unit,
    onStartScan: () -> Unit,
    onDurationSettingChanged: (Long) -> Unit,
    onSizeSettingChanged: (Long) -> Unit,
    onScanAgainClick: () -> Unit,
    onTrackClick: (Track) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            if (model.showScannerSettings) {
                NavigationTopBar(
                    title = "Scan Music",
                    onNavigateBack = onHideScanSettings
                )
            } else {
                AppTopBar(
                    onScanClick = onShowScanSettings
                )
            }
        }
    ) { paddingValues ->
        when {
            model.isScanning -> {
                ScanningContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            model.showScannerSettings -> {
                ScanSettingsContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    ignoreDurationSeconds = model.ignoreDurationSecondsSetting,
                    ignoreSizeBytes = model.ignoreSizeBytesSetting,
                    onDurationSettingChanged = onDurationSettingChanged,
                    onSizeSettingChanged = onSizeSettingChanged,
                    onScanClick = onStartScan
                )
            }

            model.trackList.isEmpty() && model.hasScannedAtLeastOnce -> {
                EmptyScanResultsContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    onScanAgainClick = onScanAgainClick
                )
            }

            model.trackList.isEmpty() -> {
                EmptyInitialContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            else -> {
                TrackListContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    tracks = model.trackList,
                    onTrackClick = onTrackClick
                )
            }
        }
    }
}
