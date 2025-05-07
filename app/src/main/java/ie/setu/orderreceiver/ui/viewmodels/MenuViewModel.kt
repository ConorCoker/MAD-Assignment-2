package ie.setu.orderreceiver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.orderreceiver.data.dao.MenuDao
import ie.setu.orderreceiver.data.entities.MenuItem
import ie.setu.orderreceiver.data.entities.Order
import ie.setu.orderreceiver.utils.Categories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(private val dao: MenuDao) : ViewModel() {
    private val _menu = MutableStateFlow<List<MenuItem>>(emptyList())
    val menu: StateFlow<List<MenuItem>> get() = _menu.asStateFlow()

    // Orders (Assignment 2)
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders.asStateFlow()

    private var fullMenuList: List<MenuItem> = emptyList()

    init {
        loadMenuItems()
    }

    private val fireStore = FirebaseFirestore.getInstance()

    fun loadMenuItems() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getMenu().collect { menuItems ->
                fullMenuList = menuItems
                _menu.value = fullMenuList
            }
        }
    }

    fun addMenuItem(menuItem: MenuItem) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertMenuItem(menuItem)
            loadMenuItems()
        }
    }

    fun deleteMenuItem(menuItem: MenuItem) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteMenuItem(menuItem)
            loadMenuItems()
        }
    }

    fun getMenuItemsByCategory(category: Categories) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getMenuItemsByCategory(category).collect { menuItems ->
                _menu.value = menuItems
            }
        }
    }

    //Firestore (Assignment 2)

    //https://firebase.google.com/docs/firestore/manage-data/add-data#kotlin_1
    fun addToOrder(menuItem: MenuItem, onAddToOrderSuccess: () -> Unit, onAddToOrderFail: () -> Unit) {
        val order = Order(
            itemId = menuItem.itemId,
            name = menuItem.name,
            price = menuItem.price,
            userId = FirebaseAuth.getInstance().currentUser!!.uid,
            imageUri = menuItem.imageUri
        )
        //https://stackoverflow.com/questions/73718391/how-to-add-data-to-the-firebase-firestore-by-generating-different-document-id-af
        fireStore.collection("orders")
            .document(order.itemId)
            .set(order)
            .addOnSuccessListener {
                Log.d("MenuViewModel", "Order successfully uploaded!")
                onAddToOrderSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("MenuViewModel", "Error uploading order", e)
                onAddToOrderFail()
            }
    }

    fun loadOrders() {
        Log.d("MenuViewModel", "loadOrders() called.")
        fireStore.collection("orders")
            .whereEqualTo("userId", FirebaseAuth.getInstance().currentUser!!.uid)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.w("MenuViewModel", "Error loading orders", exception)
                    return@addSnapshotListener
                }

                val orderList = snapshot?.documents?.map { document ->
                    document.toObject(Order::class.java) ?: Order(
                        itemId = "",
                        name = "",
                        price = 0.0,
                        userId = FirebaseAuth.getInstance().currentUser!!.uid
                    )
                } ?: emptyList()

                _orders.value = orderList
            }
    }

    fun deleteOrder(orderId: String, onSuccess: () -> Unit) {
        fireStore.collection("orders").document(orderId)
            .delete()
            .addOnSuccessListener {
                Log.d("MenuViewModel", "Order $orderId deleted successfully.")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("MenuViewModel", "Error deleting order $orderId", e)
            }
    }

    fun updateOrderStatus(orderId: String) {
        fireStore.collection("orders").document(orderId)
            .update("completed", true)
            .addOnSuccessListener {
                Log.d("MenuViewModel", "Order status updated.")
            }
            .addOnFailureListener { e ->
                Log.e("MenuViewModel", "Error updating order status", e)
            }
    }

    //https://www.youtube.com/watch?v=CfL6Dl2_dAE&ab_channel=PhilippLackner
    fun searchMenuItems(searchQuery: String) {
        _menu.value = if (searchQuery.isBlank()) {
            fullMenuList
        } else {
            fullMenuList.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }
}