package ie.setu.orderreceiver.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarms
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.data.entities.Order
import ie.setu.orderreceiver.ui.viewmodels.MenuViewModel

@Composable
fun OrdersScreen(navController: NavController, viewModel: MenuViewModel) {
    val orders = viewModel.orders.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.loadOrders() // crashing in vm attempt to load here to give firestore time to init
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            OrderPageInstructionsPanel()
        }
        items(orders) { order ->
            OrderItemRow(
                order = order,
                onDelete = {
                    viewModel.deleteOrder(order.itemId) {
                        Toast.makeText(navController.context, R.string.order_deleted, Toast.LENGTH_SHORT).show()
                        viewModel.loadOrders()
                    }
                },
                onUpdateStatus = {
                    viewModel.updateOrderStatus(order.itemId)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItemRow(
    order: Order,
    onDelete: () -> Unit,
    onUpdateStatus: () -> Unit
) {
    val dismissState = rememberDismissState()
    LaunchedEffect(dismissState.targetValue) {
        if (dismissState.targetValue == DismissValue.DismissedToStart) {
            onDelete()
        }
    }
    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        background = {
            val color = if (dismissState.targetValue == DismissValue.DismissedToStart) Color.Red else Color.Transparent
            val icon = if (dismissState.targetValue == DismissValue.DismissedToStart) Icons.Default.DeleteForever else null
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color),
                contentAlignment = Alignment.CenterEnd
            ) {
                icon?.let {
                    Icon(imageVector = it, contentDescription = null, modifier = Modifier.padding(16.dp))
                }
            }
        },
        dismissContent = {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onUpdateStatus() }
                ) {
                    AsyncImage(
                        model = order.imageUri,
                        contentDescription = order.name,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterVertically),
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(order.name, fontWeight = FontWeight.Bold)
                        Text("Price: \$${order.price}")
                        Icon(
                            imageVector = if (order.completed) Icons.Default.Check else Icons.Default.AccessAlarms,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun OrderPageInstructionsPanel() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tap to update order status, swipe to delete from firestore",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
