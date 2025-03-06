package ie.setu.orderreceiver.ui.state

import ie.setu.orderreceiver.data.entities.MenuItem

data class MenuUiState(
    val menuItems: List<MenuItem> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)
