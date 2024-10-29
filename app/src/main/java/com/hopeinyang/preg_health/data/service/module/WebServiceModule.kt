package com.hopeinyang.preg_health.data.service.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hopeinyang.preg_health.data.service.WebServerAPI
import com.hopeinyang.preg_health.data.service.WebService
import com.hopeinyang.preg_health.data.service.impl.WebServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.grpc.okhttp.OkHttpServerBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {

    @Provides
    fun providesWebServiceAPI():WebServerAPI{
        val retrofit = getRetrofit(okHttpClient)
        return retrofit.create(WebServerAPI::class.java)
    }

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("http://" + "10.0.2.2" + ":" + 5000 + "/")
            .baseUrl("https://pregnancyriskapi.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(okHttpClient!!)
            .build()
    }

    @JvmStatic
    @get:Singleton
    @get:Provides
    val okHttpClient: OkHttpClient
        get() {
            //var logging = HttpLoggingInterceptor()
            val client = OkHttpClient.Builder()
                //.connectTimeout(APIConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                //.readTimeout(APIConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                //.writeTimeout(APIConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                //client.addInterceptor(RequestInterceptor())
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            return client.build()
        }

    @Provides
    fun providesWebService(webServerAPI: WebServerAPI): WebService{
        return WebServiceImpl(webServerAPI)
    }

}