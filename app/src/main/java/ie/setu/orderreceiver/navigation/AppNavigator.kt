package ie.setu.orderreceiver.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.orderreceiver.ui.composables.BottomNavBar
import ie.setu.orderreceiver.ui.screens.AddToMenuScreen
import ie.setu.orderreceiver.ui.screens.Menu
import ie.setu.orderreceiver.ui.screens.LoginScreen
import ie.setu.orderreceiver.ui.screens.OrdersScreen
import ie.setu.orderreceiver.ui.viewmodels.MenuViewModel

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) Destinations.MENU.route else Destinations.LOGIN.route
    ) {
        composable(Destinations.LOGIN.route) {
            LoginScreen(navController)
        }
        composable(Destinations.MENU.route) {
            ScreenWithBottomNavBar(navController = navController) {
                val menuViewModel: MenuViewModel = hiltViewModel()
                Menu(
                    navController,
                    menuViewModel
                )
            }
        }
        composable(Destinations.ORDERS.route) {
            ScreenWithBottomNavBar(navController = navController) {
                OrdersScreen(
                    navController
                )
            }
        }
        composable(Destinations.ADD_TO_MENU.route) {
            ScreenWithBottomNavBar(navController = navController) {
                AddToMenuScreen(
                    navController
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenWithBottomNavBar(navController: NavController, content: @Composable () -> Unit) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) {
        content()
    }
}