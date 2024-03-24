package com.ktor.exp.data.repo

import com.ktor.exp.domain.RequestState
import com.ktor.exp.domain.model.User
import com.ktor.exp.domain.model.UserBasicInfo

interface MainRepo {
    suspend fun login(phone: String, pass: String): RequestState<User>
    suspend fun getUserList(): RequestState<List<UserBasicInfo>>
}