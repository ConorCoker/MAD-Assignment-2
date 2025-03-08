package ie.setu.orderreceiver.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.data.entities.MenuItem
import ie.setu.orderreceiver.ui.composables.CategoryPickerDialog
import ie.setu.orderreceiver.ui.composables.OrderReceiverTextField
import ie.setu.orderreceiver.ui.viewmodels.MenuViewModel
import ie.setu.orderreceiver.utils.Categories

@Composable
fun AddToMenuScreen(
    navController: NavController,
    viewModel: MenuViewModel,
    selectedPath: State<String>,
    onImagePickerRequest: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") } //double stored as String
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedPath.value.isNotBlank()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(selectedPath.value)
                    .crossfade(true)
                    .build(),
                contentDescription = "Selected Image",
                modifier = Modifier.weight(1f)
            )
        }
        Button(onClick = { onImagePickerRequest() }) {
            Text(stringResource(id = R.string.add_to_menu))
        }
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
        OrderReceiverTextField(value = price, onValueChange = {
            price = it
        }, label = {
            Text(text = stringResource(id = R.string.price))
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
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
        CategoryPickerDialog(selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            showDialog = showDialog,
            onDismissDialog = { showDialog = false })
        val context = LocalContext.current
        Button(onClick = {
            addToMenu(
                itemName = itemName,
                itemDescription = itemDescription,
                price = price,
                category = selectedCategory,
                viewModel = viewModel,
                imageUri = selectedPath.value,
                onAdditionFailed = {
                    Toast.makeText(context, R.string.item_addition_failed, Toast.LENGTH_SHORT)
                        .show()
                },
                onAdditionSuccess = {
                    Toast.makeText(context, R.string.item_addition_success, Toast.LENGTH_SHORT)
                        .show()
                    itemName = ""
                    itemDescription = ""
                    price = ""
                }
            )
        }) {
            Text(stringResource(id = R.string.add_to_menu))
        }
    }
}

fun addToMenu(
    itemName: String,
    itemDescription: String?,
    price: String,
    category: Categories,
    viewModel: MenuViewModel,
    imageUri: String,
    onAdditionFailed: () -> Unit,
    onAdditionSuccess: () -> Unit
) {
    val priceAsDouble = price.toDoubleOrNull()
    if (itemName.isNotBlank() && priceAsDouble != null) {
        viewModel.addMenuItem(
            MenuItem(
                name = itemName,
                description = itemDescription,
                price = priceAsDouble,
                category = category,
                imageUri = imageUri
            )
        )
        onAdditionSuccess()
    } else {
        onAdditionFailed()
    }
}