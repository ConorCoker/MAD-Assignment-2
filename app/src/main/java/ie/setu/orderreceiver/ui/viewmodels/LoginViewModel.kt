package ie.setu.orderreceiver.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.CustomCredential

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest
) : ViewModel() {

    var loginState by mutableStateOf<LoginResult>(LoginResult.Idle)
        private set

    fun signInWithGoogle(context: Context, onSignInWithGoogleSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = context
                )
                handleSignIn(result)
                onSignInWithGoogleSuccess()
            } catch (e: GetCredentialException) {
                loginState = LoginResult.Failure("Google sign-in failed")
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken
                        loginWithFirebase(googleIdToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        loginState = LoginResult.Failure("Invalid token")
                    }
                } else {
                    loginState = LoginResult.Failure("Unexpected credential type")
                }
            }
            else -> {
                loginState = LoginResult.Failure("Unknown credential type")
            }
        }
    }

    private fun loginWithFirebase(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                loginState = if (task.isSuccessful) {
                    LoginResult.Success
                } else {
                    LoginResult.Failure(task.exception?.localizedMessage ?: "Unknown error")
                }
            }
    }
}

sealed class LoginResult {
    object Idle : LoginResult()
    object Success : LoginResult()
    data class Failure(val error: String) : LoginResult()
}