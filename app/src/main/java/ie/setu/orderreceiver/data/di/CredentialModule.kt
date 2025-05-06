package ie.setu.orderreceiver.data.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.setu.orderreceiver.R

@Module
@InstallIn(SingletonComponent::class)
object CredentialModule {

    @Provides
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager =
        CredentialManager.create(context)

    @Provides
    fun provideGoogleIdOptions(@ApplicationContext context: Context): GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .build()

    @Provides
    fun getCredentialRequest(option: GetGoogleIdOption): GetCredentialRequest =
        GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()
}