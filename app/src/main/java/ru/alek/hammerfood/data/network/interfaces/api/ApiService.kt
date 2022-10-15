package ru.alek.hammerfood.data.network.interfaces.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.alek.hammerfood.domain.model.ServerResponse
import ru.alek.hammerfood.utils.MainUtils

interface ApiService {

    @GET("random")
    suspend fun loadRandomRecipes(@Query("apiKey") apiKey: String = MainUtils.API_KEY,
                                  @Query("number") number: Int = 15): Response<ServerResponse>

}