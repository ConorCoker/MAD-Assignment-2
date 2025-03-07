package ie.setu.orderreceiver.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.OutdoorGrill
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.ui.graphics.vector.ImageVector
import ie.setu.orderreceiver.R

enum class Categories(
    val categoryNameResId: Int,
    val categoryIcon: ImageVector
) {
    STARTERS(
        categoryNameResId = R.string.starters,
        categoryIcon = Icons.Default.Fastfood
    ),
    MAIN_COURSE(
        categoryNameResId = R.string.main_course,
        categoryIcon = Icons.Default.DinnerDining
    ),
    DESSERTS(
        categoryNameResId = R.string.desserts,
        categoryIcon = Icons.Default.Cake
    ),
    BEVERAGES(
        categoryNameResId = R.string.beverages,
        categoryIcon = Icons.Default.LocalDrink
    ),
    SIDES(
        categoryNameResId = R.string.sides,
        categoryIcon = Icons.Default.LunchDining
    ),
    SALADS(
        categoryNameResId = R.string.salads,
        categoryIcon = Icons.Default.Eco
    ),
    SOUPS(
        categoryNameResId = R.string.soups,
        categoryIcon = Icons.Default.RamenDining
    ),
    SEAFOOD(
        categoryNameResId = R.string.seafood,
        categoryIcon = Icons.Default.SetMeal
    ),
    VEGETARIAN(
        categoryNameResId = R.string.vegetarian,
        categoryIcon = Icons.Default.Grass
    ),
    BREAKFAST(
        categoryNameResId = R.string.breakfast,
        categoryIcon = Icons.Default.BreakfastDining
    ),
    GRILL(
        categoryNameResId = R.string.grill,
        categoryIcon = Icons.Default.OutdoorGrill
    ),
    PASTA(
        categoryNameResId = R.string.pasta,
        categoryIcon = Icons.Default.DinnerDining
    ),
    PIZZA(
        categoryNameResId = R.string.pizza,
        categoryIcon = Icons.Default.LocalPizza
    )
}