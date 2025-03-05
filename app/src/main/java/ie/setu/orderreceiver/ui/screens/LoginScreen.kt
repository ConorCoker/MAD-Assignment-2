package ie.setu.orderreceiver.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.ui.composables.OrderReceiverButton
import ie.setu.orderreceiver.ui.composables.OrderReceiverTextField

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OrderReceiverTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.username)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Profile Icon"
                )
            },
            keyboardOptions = KeyboardOptions(),
            isError = false
        )
        OrderReceiverTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon"
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = false
        )
        val context = LocalContext.current
        OrderReceiverButton(stringResource(R.string.login)) {
            login(
                email = email,
                password = password,
                onLoginAttemptFailed = {
                    Log.w(TAG, "$it ?: ${context.getString(R.string.login_failed)}")
                    Toast.makeText(
                        context,
                        context.getString(R.string.login_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                onLoginAttemptSuccess = {
                    navController.navigate("home")
                }
            )
        }
    }
}

private fun login(
    email: String,
    password: String,
    onLoginAttemptFailed: (String?) -> Unit,
    onLoginAttemptSuccess: () -> Unit
) {
    FirebaseAuth.getInstance()
        .signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onLoginAttemptSuccess()
            } else {
                onLoginAttemptFailed(task.exception?.localizedMessage)
            }
        }
}

const val TAG = "LoginScreen"

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
//    LoginScreen()
}