package ie.setu.orderreceiver.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.navigation.Destinations
import ie.setu.orderreceiver.ui.composables.OrderReceiverButton
import ie.setu.orderreceiver.ui.composables.OrderReceiverTextField
import ie.setu.orderreceiver.ui.viewmodels.LoginViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                Icon(Icons.Default.AccountBox, contentDescription = "Email Icon")
            },
            keyboardOptions = KeyboardOptions.Default,
            isError = false
        )
        OrderReceiverTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password Icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = false
        )
        OrderReceiverTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repeat Password") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Repeat Password Icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = false
        )
        OrderReceiverButton(stringResource(R.string.register)) {
            if (password != repeatPassword) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@OrderReceiverButton
            }

            register(
                email = email,
                password = password,
                onRegisterFailed = {
                    Log.w("RegisterScreen", "$it")
                    Toast.makeText(context, context.getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
                },
                onRegisterSuccess = {
                    navController.navigate(Destinations.MENU.route) {
                        popUpTo(Destinations.REGISTER.route) { inclusive = true }
                    }
                }
            )
        }
        OrderReceiverButton("Sign up with Google") {
            viewModel.signInWithGoogle(context)
        }
        OrderReceiverButton(stringResource(id = R.string.account_already)) {
            navController.navigate(Destinations.LOGIN.route) {
                popUpTo(Destinations.REGISTER.route) { inclusive = true }
            }
        }
    }
}


private fun register(
    email: String,
    password: String,
    onRegisterFailed: (String?) -> Unit,
    onRegisterSuccess: () -> Unit
) {
    FirebaseAuth.getInstance()
        .createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onRegisterSuccess()
            } else {
                onRegisterFailed(task.exception?.localizedMessage)
            }
        }
}