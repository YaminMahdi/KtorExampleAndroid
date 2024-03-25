package com.ktor.exp.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.ktor.exp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.encodeCookieValue
import io.ktor.serialization.gson.gson
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun provideHttpClient(@ApplicationContext context: Context): HttpClient {
        val okhttpEngine = OkHttp.create {
            addInterceptor(ChuckerInterceptor(context))
        }
        return HttpClient(okhttpEngine){
            install(Logging){
                level= LogLevel.ALL
            }
            defaultRequest{
                url(Constants.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation){
//                json(Json {
//                    explicitNulls = false
//                    encodeDefaults = true
//                })
//                gson{
//                    serializeNulls()
//                }
                jackson {
                    setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP))
                }
            }
        }
    }
}
