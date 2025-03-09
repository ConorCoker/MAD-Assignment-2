package ie.setu.orderreceiver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.orderreceiver.data.dao.MenuDao
import ie.setu.orderreceiver.data.entities.MenuItem
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

    init {
        loadMenuItems()
    }

    fun loadMenuItems() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getMenu().collect { menuItems ->
                _menu.value = menuItems
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
}