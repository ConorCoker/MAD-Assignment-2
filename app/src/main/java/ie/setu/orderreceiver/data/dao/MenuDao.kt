package ie.setu.orderreceiver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ie.setu.orderreceiver.data.entities.MenuItem

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getMenu():List<MenuItem>

    @Query("SELECT * FROM menu WHERE category = :category")
    fun getMenuItemsByCategory(category:String):List<MenuItem>

    @Insert
    fun insertMenuItem(vararg menuItems:MenuItem)
}