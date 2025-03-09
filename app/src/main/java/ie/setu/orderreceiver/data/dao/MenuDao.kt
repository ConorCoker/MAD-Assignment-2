package ie.setu.orderreceiver.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ie.setu.orderreceiver.data.entities.MenuItem
import ie.setu.orderreceiver.utils.Categories
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getMenu(): Flow<List<MenuItem>>

    @Query("SELECT * FROM menu WHERE category = :category")
    fun getMenuItemsByCategory(category: Categories): Flow<List<MenuItem>>

    @Insert
    fun insertMenuItem(menuItem: MenuItem)

    @Delete
    fun deleteMenuItem(itemToDelete: MenuItem)
}