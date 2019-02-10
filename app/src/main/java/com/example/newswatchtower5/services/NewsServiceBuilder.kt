package com.example.newswatchtower5.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsServiceBuilder {
    /**
     * The base url to the API
     */
    private val URL = "https://newsapi.org/v2/"

    /**
     * Create logger.
     */
    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    /**
     * Create OkHttp Client.
     */
    private val okhttp = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)


    /**
     * The retrofit instance
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).client(okhttp.build())
        .build()

    /**
     * Helper method to help us build services and creates an implementation of our interfaces.
     *
     * @param serviceType object of service.
     * @param <S>         generic type.
     * @return retrofit.create(serviceType)
    </S> */
    fun <S> builderService(serviceType: Class<S>): S {
        return retrofit.create(serviceType)
    }
}
