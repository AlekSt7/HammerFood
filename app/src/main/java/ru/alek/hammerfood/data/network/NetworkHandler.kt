package ru.alek.hammerfood.data.network

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alek.hammerfood.App
import ru.alek.hammerfood.data.network.interfaces.api.ApiService
import ru.alek.hammerfood.utils.MainUtils
import java.util.concurrent.TimeUnit

object NetworkHandler {

    private val retrofit by lazy { Retrofit.Builder()
        .baseUrl(MainUtils.BASE_URL)
        .client(buildOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build() }

    /**
     * Здесь формирую OkHttpClient с встроенным кэшированием запросов.
     * На самом деле, считаю что такое кэширование не совсем правильное, т.к. в будущем очень сложно манипулировать такой кэшированной информацией,
     * в т.ч. проводить поиск и т.п., но для данного тестового задания подходит. Как альтернатива может подойти Room или RemoteMediator с Room из Paging3
     */
    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(Cache(App.instance.cacheDir, 10 * 1024 * 1024))
            .addInterceptor(offlineInterceptor())
            .build()
    }

    private fun offlineInterceptor() = Interceptor {
        var request: Request = it.request()
        if (!App.hasNetwork()) {
            val cacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
        }
        it.proceed(request)
    }


    val api: ApiService
        get(){
            return retrofit.create(ApiService::class.java)
        }

}