package ie.setu.orderreceiver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ie.setu.orderreceiver.utils.Categories
import java.util.UUID

@Entity(tableName = "menu")
data class MenuItem(
    @PrimaryKey val itemId: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val price: Double,
    val category: Categories, // e.g., "Drinks", "Mains", "Desserts"
    val imageUri: String? = null,
    val available: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)
