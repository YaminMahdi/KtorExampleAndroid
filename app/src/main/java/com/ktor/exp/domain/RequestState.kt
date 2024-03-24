package com.ktor.exp.domain


sealed interface RequestState<out Data> {
    data class Success<out Data>(val data: Data): RequestState<Data>
    data class Error(val error: String): RequestState<Nothing>
}