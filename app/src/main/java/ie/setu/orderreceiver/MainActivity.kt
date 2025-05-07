package ie.setu.orderreceiver

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.orderreceiver.navigation.AppNavigator
import ie.setu.orderreceiver.ui.theme.OrderReceiverTheme
import ie.setu.orderreceiver.utils.SaveToFileSystem
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var selectedUriOnAddToMenuScreen: MutableState<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            selectedUriOnAddToMenuScreen = remember { mutableStateOf("") }
            OrderReceiverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigator(onImagePickerRequest = {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }, uriPathForAddToMenuScreen = selectedUriOnAddToMenuScreen)
                }
            }
        }
        // Pick Media to get photos for menu items in AddToMenuScreen
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    lifecycleScope.launch {
                        val uploadedUrl = SaveToFileSystem.FirebaseUploader.uploadImageToFirebase(uri, this@MainActivity)
                        if (uploadedUrl != null) {
                            selectedUriOnAddToMenuScreen.value = uploadedUrl
                        }
                    }
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OrderReceiverTheme {
        Greeting("Android")
    }
}