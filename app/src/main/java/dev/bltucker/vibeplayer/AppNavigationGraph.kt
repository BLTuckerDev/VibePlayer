package dev.bltucker.vibeplayer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.bltucker.vibeplayer.home.HOME_SCREEN_ROUTE
import dev.bltucker.vibeplayer.home.homeScreen
import dev.bltucker.vibeplayer.permission.navigateToPermission
import dev.bltucker.vibeplayer.permission.permissionScreen
import dev.bltucker.vibeplayer.player.navigateToPlayer
import dev.bltucker.vibeplayer.player.playerScreen

@Composable
fun AppNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN_ROUTE
    ) {
        homeScreen(
            onNavigateToPermissions = {
                navController.navigateToPermission()
            },
            onNavigateToPlayer = { trackId ->
                navController.navigateToPlayer(trackId)
            }
        )

        permissionScreen(
            onPermissionGranted = {
                navController.popBackStack()
            }
        )

        playerScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}
