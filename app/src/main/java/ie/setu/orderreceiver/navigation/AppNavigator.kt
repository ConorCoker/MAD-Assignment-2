package ie.setu.orderreceiver.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.ui.composables.BottomNavBar
import ie.setu.orderreceiver.ui.screens.AddToMenuScreen
import ie.setu.orderreceiver.ui.screens.Menu
import ie.setu.orderreceiver.ui.screens.LoginScreen
import ie.setu.orderreceiver.ui.screens.OrdersScreen
import ie.setu.orderreceiver.ui.screens.RegisterScreen
import ie.setu.orderreceiver.ui.viewmodels.MenuViewModel

@Composable
fun AppNavigator(
    uriPathForAddToMenuScreen: State<String>,
    onImagePickerRequest: () -> Unit
) {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) Destinations.MENU.route else Destinations.REGISTER.route
    ) {
        composable(Destinations.REGISTER.route) {
            RegisterScreen(navController)
        }
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
            val menuViewModel: MenuViewModel = hiltViewModel()
            ScreenWithBottomNavBar(navController = navController) {
                AddToMenuScreen(
                    navController,
                    menuViewModel,
                    uriPathForAddToMenuScreen,
                    onImagePickerRequest = {
                        onImagePickerRequest()
                    }
                )
            }
        }
    }
}

@Composable
fun ScreenWithBottomNavBar(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.food_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(paddingValues)
        ) {
            content()
        }
    }
}
