package ie.setu.orderreceiver.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
fun Menu(
    navController: NavController,
    viewModel: MenuViewModel
) {
    Column {
        Menu(viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(viewModel: MenuViewModel) {
    val menuItems by viewModel.menu.collectAsState()
    var selectedCategoryFilter by remember { mutableStateOf<Categories?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SwipeInstructionsPanel(
                    menuLabel = if (selectedCategoryFilter == null)
                        stringResource(id = R.string.menu)
                    else
                        stringResource(id = selectedCategoryFilter!!.categoryNameResId)
                )
            }
            item {
                OrderReceiverTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.searchMenuItems(it)
                    },
                    label = { Text(stringResource(id = R.string.search_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { viewModel.searchMenuItems(searchQuery) }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                )
            }

            items(menuItems) { item ->
                MenuItemRow(
                    menuItem = item,
                    onDismiss = { menuItem, dismissValue ->
                        if (dismissValue == DismissValue.DismissedToStart) {
                            viewModel.deleteMenuItem(menuItem)
                        }
                    },
                    onAddToOrder = {
                        viewModel.addToOrder(it,
                            onAddToOrderSuccess = {
                                Toast.makeText(context, R.string.order_success, Toast.LENGTH_SHORT).show()
                            },
                            onAddToOrderFail = {
                                Toast.makeText(context, R.string.order_failed, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
            }
        }
        CategoryPickerDialog(
            selectedCategory = selectedCategoryFilter,
            onCategorySelected = { category ->
                Log.d("FILTER", "User has selected the category ${category.name}")
                selectedCategoryFilter = category
                viewModel.getMenuItemsByCategory(category)
                showDialog = false
            },
            showDialog = showDialog,
            onConfirmDialog = {
                showDialog = false
                selectedCategoryFilter = null
                viewModel.loadMenuItems()
                Log.d("FILTER", "Closing dialog and resetting VM menu items to all")
            },
            dialogButtonTextResId = R.string.reset_filter,
            onDismissDialog = {
                showDialog = false
            }
        )
        Button(onClick = { showDialog = true }) {
            Text(text = stringResource(id = R.string.filter_by_category))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemRow(
    menuItem: MenuItem,
    onAddToOrder: (menuItem: MenuItem) -> Unit,
    onDismiss: (menuItem: MenuItem, dismissValue: DismissValue) -> Unit
) {
    val dismissState = rememberDismissState()

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        background = {
            val color = if (dismissState.targetValue == DismissValue.DismissedToStart) Color.Red else Color.Transparent
            val alignment = if (dismissState.targetValue == DismissValue.DismissedToStart) Alignment.CenterEnd else Alignment.CenterStart

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color),
                contentAlignment = alignment
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAddToOrder(menuItem) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(menuItem.imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = menuItem.name,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(100.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = menuItem.name, fontWeight = FontWeight.Bold)
                        menuItem.description?.let {
                            Text(text = it, fontStyle = FontStyle.Italic)
                        }
                        Row {
                            Icon(
                                imageVector = menuItem.category.categoryIcon,
                                contentDescription = stringResource(id = menuItem.category.categoryNameResId),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(text = stringResource(id = menuItem.category.categoryNameResId))
                        }
                        Text(
                            text = menuItem.price.toString(),
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    )

    if (dismissState.targetValue == DismissValue.DismissedToStart) {
        onDismiss(menuItem, dismissState.currentValue)
    }
}

@Composable
fun SwipeInstructionsPanel(menuLabel: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(24.dp))
                Icon(imageVector = Icons.Default.TouchApp, contentDescription = null, modifier = Modifier.size(24.dp))
            }
            Text(text = menuLabel, fontWeight = FontWeight.Bold)
            Column {
                Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null, modifier = Modifier.size(24.dp))
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(24.dp))
            }
        }
    }
}