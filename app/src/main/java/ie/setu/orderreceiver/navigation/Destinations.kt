package ie.setu.orderreceiver.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import ie.setu.orderreceiver.R

enum class Destinations(
    val route: String,
    val icon: ImageVector? = null,
    val appearOnNavBar: Boolean = false,
    val displayTextResId: Int = R.string.blank
) {
    REGISTER(route = "register"),
    LOGIN(route = "login"),
    MENU(
        route = "menu",
        icon = Icons.Default.Menu,
        appearOnNavBar = true,
        displayTextResId = R.string.menu
    ),
    ORDERS(
        route = "orders",
        icon = Icons.Default.Face,
        appearOnNavBar = true,
        displayTextResId = R.string.orders
    ),
    ADD_TO_MENU(
        route = "add_to_menu",
        icon = Icons.Default.Add,
        appearOnNavBar = true,
        displayTextResId = R.string.add_to_menu
    )
}