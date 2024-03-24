package com.ktor.exp.data.repo

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ktor.exp.data.dto.UserListDto
import com.ktor.exp.domain.RequestState
import com.ktor.exp.domain.model.User
import com.ktor.exp.domain.model.UserBasicInfo
import com.ktor.exp.dto.LoginDto
import com.ktor.exp.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val ktorClient: HttpClient,
    @ApplicationContext val context: Context
): MainRepo {
    private val Context.dataStore by preferencesDataStore(
        name = Constants.USER_PREFERENCES_NAME
    )
    override suspend fun login(phone: String, pass: String): RequestState<User> {
         return withContext(Dispatchers.IO){
            try {
                val response=ktorClient.submitForm(
                    url = "api/login",
                    formParameters = parameters {
                        append("phone", phone)
                        append("password", pass)
                        append("version", Constants.API_VERSION)
                        append("vendor_id", Constants.VENDOR_ID)
                    }
                )
                val body=  response.body<LoginDto>()
                val data=  body.data?.toUser() ?: User()
                Log.d("login", "login: $data")
                context.dataStore.edit {preferences->
                    preferences[stringPreferencesKey("token")] = data.token
                }
                if(body.status == "success")
                    RequestState.Success(data)
                else
                    RequestState.Error(body.message)
            }
            catch (e: Exception){
                e.printStackTrace()
                RequestState.Error(e.message.toString())
            }
        }
    }

    override suspend fun getUserList(): RequestState<List<UserBasicInfo>> {
        return withContext(Dispatchers.IO){
            try {
                val token= context.dataStore.data
                    .catch { emit(emptyPreferences()) }
                    .map { it[stringPreferencesKey("token")] }
                    .first() ?: ""
                Log.d("getUserList", "getUserList: $token")
                val response=ktorClient.get("api/user-list"){
                    header(HttpHeaders.Authorization, token)
                    parameter("page", 1)
                    parameter("version", Constants.API_VERSION)
                    parameter("vendor_id", Constants.VENDOR_ID)
                }
                val body=  response.body<UserListDto>()
                Log.d("getUserList", "getUserList: $body")
                val data=  body.data?.map{ it.toUserBasicInfo()} ?: emptyList()

                if(body.status == "success" && data.isNotEmpty())
                    RequestState.Success(data)
                else
                    RequestState.Error(body.message)
            }
            catch (e: Exception){
                e.printStackTrace()
                RequestState.Error(e.message.toString())
            }
        }

    }
}