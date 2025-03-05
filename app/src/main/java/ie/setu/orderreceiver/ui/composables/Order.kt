package ie.setu.orderreceiver.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
private fun Order() {
    Column {
        // Order 1: Cheeseburger with some special requests
        OrderItem(
            itemName = "Cheeseburger",
            specialRequests = mapOf(
                0 to "No Pickles",  // Special request type 0 (maybe urgent or allergy-related)
                1 to "Extra Cheese", // Special request type 1 (normal requests)
                2 to "No Onions"     // Special request type 2 (another type)
            )
        )

        // Order 2: Veggie Burger with different special requests
        OrderItem(
            itemName = "Veggie Burger",
            specialRequests = mapOf(
                0 to "Add Avocado",  // Special request type 0
                1 to "No Mayo",      // Special request type 1
                2 to "Lettuce Only"  // Special request type 2
            )
        )

        // Order 3: Classic Hot Dog
        OrderItem(
            itemName = "Classic Hot Dog",
            specialRequests = mapOf(
                0 to "Extra Mustard",  // Special request type 0
                1 to "No Ketchup"      // Special request type 1
            )
        )

        // Order 4: Caesar Salad
        OrderItem(
            itemName = "Caesar Salad",
            specialRequests = mapOf(
                2 to "Add Grilled Chicken",  // Special request type 2
                1 to "No Dressing"          // Special request type 1
            )
        )

        // Order 5: Pizza (Margherita)
        OrderItem(
            itemName = "Margherita Pizza",
            specialRequests = mapOf(
                0 to "Add Extra Basil",  // Special request type 0
                2 to "No Mozzarella"     // Special request type 2
            )
        )
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
private fun PreviewOrder() {
    Order()
}