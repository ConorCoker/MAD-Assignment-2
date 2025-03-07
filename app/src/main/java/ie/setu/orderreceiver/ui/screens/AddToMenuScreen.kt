package ie.setu.orderreceiver.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.data.entities.MenuItem
import ie.setu.orderreceiver.ui.composables.CategoryPickerDialog
import ie.setu.orderreceiver.ui.composables.OrderReceiverTextField
import ie.setu.orderreceiver.ui.viewmodels.MenuViewModel
import ie.setu.orderreceiver.utils.Categories

@Composable
fun AddToMenuScreen(
    navController: NavController,
    viewModel: MenuViewModel
) {
    var itemName by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var price by remember { mutableDoubleStateOf(0.0) }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OrderReceiverTextField(value = itemName, onValueChange = {
            itemName = it
        }, label = {
            Text(text = stringResource(id = R.string.item_name))
        })
        OrderReceiverTextField(value = itemDescription, onValueChange = {
            itemDescription = it
        }, label = {
            Text(text = stringResource(id = R.string.description))
        })
        OrderReceiverTextField(value = price.toString(), onValueChange = {
            price = it.toDouble()
        }, label = {
            Text(text = stringResource(id = R.string.price))
        })
        var selectedCategory by remember { mutableStateOf(Categories.STARTERS) }
        var showDialog by remember { mutableStateOf(false) }
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.clickable { showDialog = true }) {
            Text(
                text = stringResource(id = R.string.selected_category) + ": ",
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = selectedCategory.categoryIcon,
                contentDescription = stringResource(id = R.string.pick_category)
            )
            Text(
                text = stringResource(id = selectedCategory.categoryNameResId)
            )
        }
        CategoryPickerDialog(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            showDialog = showDialog,
            onDismissDialog = { showDialog = false })
        Button(onClick = {
            //on click button
        }) {
            Text("Add Menu Item")
        }
    }
}

fun addToMenu(
    itemName: String,
    itemDescription: String?,
    price: Double,
    category: Categories,
    viewModel: MenuViewModel
) {
    viewModel.addMenuItem(
        MenuItem(
            name = itemName,
            description = itemDescription,
            price = price,
            category = category
        )
    )
}