package ie.setu.orderreceiver.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ie.setu.orderreceiver.data.dao.MenuDao
import ie.setu.orderreceiver.data.entities.MenuItem

@Database(entities = [MenuItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
}