package com.example.demotwo.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.demotwo.BuildConfig
import com.example.demotwo.retrofit.ServerConstants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

object RetrofitUtils {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getHttpClient(appContext: Context): OkHttpClient {

        val cacheSize = (10 * 1024 * 1024).toLong()
        val cache = Cache(appContext.cacheDir, cacheSize)

        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .hostnameVerifier(HostVerifier())

        val addInterceptor = httpClient
            .addInterceptor(Interceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .build()
                Log.d("Request>>>",request.toString())
                chain.proceed(request)
            })
        if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(interceptor)
        }
        return addInterceptor.build()
    }

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)

    fun callRetrofit(appContext: Context): RetrofitInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = builder
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getHttpClient(appContext))
            .build()
        return retrofit.create(RetrofitInterface::class.java)
    }

    class HostVerifier : HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        override fun verify(hostname: String, session: SSLSession?): Boolean {
            return true
        }
    }
}