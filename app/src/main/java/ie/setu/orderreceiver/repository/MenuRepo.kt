package ie.setu.orderreceiver.repository

import ie.setu.orderreceiver.data.entities.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuRepo {
    suspend fun getMenu(): Flow<List<MenuItem>>

    suspend fun getMenuItemsByCategory(category:String):Flow<List<MenuItem>>

    suspend fun insertMenuItem(menuItem:MenuItem)

}