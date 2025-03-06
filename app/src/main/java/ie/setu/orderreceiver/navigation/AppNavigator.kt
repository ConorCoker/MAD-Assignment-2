package ie.setu.orderreceiver.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.orderreceiver.ui.screens.Home
import ie.setu.orderreceiver.ui.screens.LoginScreen
import ie.setu.orderreceiver.ui.viewmodels.MenuViewModel

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) "home" else "login"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            val menuViewModel: MenuViewModel = hiltViewModel()
            Home(navController, menuViewModel)
        }
    }
}