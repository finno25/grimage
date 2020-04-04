package com.aura.co99.networks

import android.util.Log
import androidx.multidex.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {
    private val TAG = "BaseNetworkAgent"

    private val mInterceptor = Interceptor { chain ->
        val original = chain.request()
        val builder: Request.Builder = constructRequestHeader(original)
        val newRequest = builder.method(original.method(), original.body()).build()
        if (BuildConfig.DEBUG) {
            Log.d(
                TAG,
                String.format(
                    "Request= %s, %s, %s",
                    newRequest.url(),
                    newRequest.headers(),
                    newRequest.body()
                )
            )
        }
        chain.proceed(newRequest)
    }

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(getOkHttpClient())
        .build()

    private fun getOkHttpClient(): OkHttpClient? {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(mInterceptor);
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(createLogginInterceptor())
        }
        return httpClient.build()
    }

    private fun createLogginInterceptor(): HttpLoggingInterceptor? {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    protected fun constructRequestHeader(original: Request): Request.Builder {
        val builder = original.newBuilder()
        return builder
    }

    fun <S> createService(serviceClass: Class<S>?): S {
        return retrofit.create(serviceClass)
    }

    fun getApiInterface(): ApiInterface {
        return createService(ApiInterface::class.java)
    }

    companion object {
        var instance : RetrofitService? = null
        get() {
            if(field == null) {
                field = RetrofitService()
            }
            return field
        }
    }
}