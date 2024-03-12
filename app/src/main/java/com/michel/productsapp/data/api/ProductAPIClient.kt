package com.michel.productsapp.data.api

import com.michel.productsapp.data.ssl.SSLSocket
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://dummyjson.com/"

class ProductAPIClient {

    // Returns a client through which requests will be made
    fun getClient(): ProductAPI {

        val requestInterceptor = Interceptor{chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        // To avoid untrusted certificate exception let's use custom SSLSocket class
        val sslSocket = SSLSocket()

        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslSocket.getFactory(), sslSocket.x509TrustManager)
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
    }

}