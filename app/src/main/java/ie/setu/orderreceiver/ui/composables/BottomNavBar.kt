package ie.setu.orderreceiver.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ie.setu.orderreceiver.navigation.Destinations
import ie.setu.orderreceiver.utils.fakeNavHostController

@Composable
fun BottomNavBar(navController: NavController) {
    BottomAppBar {
        for (destination in Destinations.entries) {
            if (destination.appearOnNavBar) {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == destination.route,
                    onClick = { navController.navigate(destination.route) },
                    icon = {
                        Icon(
                            imageVector = destination.icon ?: Icons.Default.Warning,
                            contentDescription = destination.name
                        )
                    }, label = {
                        Text(text = stringResource(id = destination.displayTextResId))
                    })
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun BottomNavBarPreview() {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        BottomNavBar(fakeNavHostController(Destinations.entries))
    }
}

