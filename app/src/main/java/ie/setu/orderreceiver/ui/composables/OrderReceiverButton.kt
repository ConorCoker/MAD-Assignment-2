package ie.setu.orderreceiver.ui.composables

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun OrderReceiverButton(
    buttonText: String,
    onClick: () -> Unit
) {

    Button(onClick = { onClick() }) {
        Text(buttonText)
    }
}