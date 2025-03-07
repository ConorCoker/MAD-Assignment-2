package ie.setu.orderreceiver.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ie.setu.orderreceiver.utils.Categories
import ie.setu.orderreceiver.R

@Composable
fun CategoryPickerDialog(
    selectedCategory: Categories,
    onCategorySelected: (Categories) -> Unit,
    showDialog: Boolean,
    onDismissDialog: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismissDialog() },
            title = { Text(stringResource(id = R.string.pick_category)) },
            text = {
                Column {
                    Categories.entries.forEach { category ->
                        TextButton(
                            onClick = {
                                onCategorySelected(category)
                                onDismissDialog()
                            }
                        ) {
                            Icon(
                                imageVector = category.categoryIcon,
                                contentDescription = stringResource(
                                    id = category.categoryNameResId
                                )
                            )
                            Text(
                                stringResource(id = category.categoryNameResId),
                                fontWeight = if (category == selectedCategory) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { onDismissDialog() }) {
                    Text(stringResource(id = R.string.close))
                }
            }
        )
    }
}