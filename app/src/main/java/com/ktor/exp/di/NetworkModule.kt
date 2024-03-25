package com.ktor.exp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.ktor.exp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.jackson.jackson
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
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
