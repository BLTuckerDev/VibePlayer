package dev.bltucker.vibeplayer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.bltucker.vibeplayer.home.HOME_SCREEN_ROUTE
import dev.bltucker.vibeplayer.home.homeScreen
import dev.bltucker.vibeplayer.permission.navigateToPermission
import dev.bltucker.vibeplayer.permission.permissionScreen

@Composable
fun AppNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN_ROUTE
    ) {
        homeScreen(
            onNavigateToPermissions = {
                navController.navigateToPermission()
            }
        )

        permissionScreen(
            onPermissionGranted = {
                navController.popBackStack()
            }
        )
    }
}
