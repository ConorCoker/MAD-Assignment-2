package ie.setu.orderreceiver.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.data.entities.MenuItem
import ie.setu.orderreceiver.ui.viewmodels.MenuViewModel

@Composable
fun Home(
    navController: NavController,
    viewModel: MenuViewModel
) {
    Column {
        Menu(viewModel = viewModel)
    }
}

@Composable
fun Menu(viewModel: MenuViewModel) {
    val menuItems by viewModel.menu.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(menuItems) { item ->
            MenuItemRow(menuItem = item)
        }
        item {
            Button(onClick = {
                val newItem = MenuItem(name = "New Dish", price = 9.99, category = "Appetizer")
                viewModel.addMenuItem(newItem)
            }) {
                Text("Add Menu Item")
            }
        }
    }
}

@Composable
fun MenuItemRow(menuItem: MenuItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = menuItem.name,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = menuItem.name,
                    fontWeight = FontWeight.Bold
                )
                if (menuItem.description != null) {
                    Text(
                        text = menuItem.description,
                        fontStyle = FontStyle.Italic
                    )
                }
                Text(
                    text = menuItem.price.toString(),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuItemRow() {
    MenuItemRow(
        menuItem = MenuItem(
            name = "Cheeseburger",
            description = "A delicious beef cheeseburger with lettuce and tomato",
            price = 8.99,
            category = "Mains",
            imageUri = "https://example.com/images/cheeseburger.jpg",
            available = true,
            timestamp = System.currentTimeMillis()
        )
    )
}