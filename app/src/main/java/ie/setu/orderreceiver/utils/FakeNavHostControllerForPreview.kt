package ie.setu.orderreceiver.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import ie.setu.orderreceiver.navigation.Destinations
import kotlin.enums.EnumEntries

@Composable
fun fakeNavHostController(
    destinations: EnumEntries<Destinations>,
    fakeCurrentLocation: String = Destinations.MENU.route
): NavHostController {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        val navGraph = navController.createGraph(
            startDestination = Destinations.MENU.route
        ) {
            for (destination in destinations) {
                composable(destination.route) {}
            }
        }
        navController.graph = navGraph
        navController.navigate(fakeCurrentLocation)
    }
    return navController
}