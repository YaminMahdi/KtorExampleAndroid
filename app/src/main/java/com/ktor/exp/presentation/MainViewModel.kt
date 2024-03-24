package com.ktor.exp.presentation

import android.content.Context
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ktor.exp.data.repo.MainRepo
import com.ktor.exp.domain.RequestState
import com.ktor.exp.domain.model.User
import com.ktor.exp.domain.model.UserBasicInfo
import com.ktor.exp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: MainRepo
): ViewModel() {
    val userInfo = savedStateHandle.getStateFlow("userInfo", User())
    val userList = savedStateHandle.getStateFlow("userList", emptyList<UserBasicInfo>())
    private val Context.dataStore by preferencesDataStore(
        name = Constants.USER_PREFERENCES_NAME
    )
    fun login(phone: String, pass: String, result: (RequestState<User>) -> Unit){
        viewModelScope.launch {
            val data = repo.login(phone, pass)
            if(data is RequestState.Success)
                savedStateHandle["userInfo"] = data.data
            result.invoke(data)
        }
    }
    fun getUserList(result: (RequestState<List<UserBasicInfo>>) -> Unit){
        viewModelScope.launch{
            val data = repo.getUserList()
            if(data is RequestState.Success)
                savedStateHandle["userList"] = data.data
            result.invoke(data)
        }
    }
}