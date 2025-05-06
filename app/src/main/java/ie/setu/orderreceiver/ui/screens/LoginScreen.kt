package ie.setu.orderreceiver.ui.screens

import ie.setu.orderreceiver.ui.viewmodels.LoginViewModel
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import ie.setu.orderreceiver.R
import ie.setu.orderreceiver.navigation.Destinations
import ie.setu.orderreceiver.ui.composables.OrderReceiverButton
import ie.setu.orderreceiver.ui.composables.OrderReceiverTextField
import ie.setu.orderreceiver.ui.viewmodels.LoginResult

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginState = viewModel.loginState

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginResult.Success -> {
                navController.navigate(Destinations.MENU.route) {
                    popUpTo(Destinations.LOGIN.route) { inclusive = true } //stops the user being able to click
                }                                        //back button and end back up on login screen
            }
            is LoginResult.Failure -> {
                Toast.makeText(context, loginState.error, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

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
                    navController.navigate(Destinations.MENU.route)
                }
            )
        }
        OrderReceiverButton("Sign in with Google") {
            viewModel.signInWithGoogle(context)
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
