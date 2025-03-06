package ie.setu.orderreceiver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ie.setu.orderreceiver.data.entities.MenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getMenu(): Flow<List<MenuItem>>

    @Query("SELECT * FROM menu WHERE category = :category")
    fun getMenuItemsByCategory(category:String):Flow<List<MenuItem>>

    @Insert
    fun insertMenuItem(menuItem:MenuItem)
}