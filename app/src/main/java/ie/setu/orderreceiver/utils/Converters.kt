package ie.setu.orderreceiver.utils

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromCategory(category: Categories): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(categoryName: String): Categories {
        return Categories.valueOf(categoryName)
    }
}
