package ie.setu.orderreceiver.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.orderreceiver.data.dao.MenuDao
import ie.setu.orderreceiver.data.entities.MenuItem
import ie.setu.orderreceiver.utils.Converters

@TypeConverters(Converters::class)
@Database(entities = [MenuItem::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
}