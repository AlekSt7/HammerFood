package ru.alek.hammerfood.data.network.repos

import ru.alek.hammerfood.data.network.interfaces.api.ApiService
import ru.alek.hammerfood.domain.interfaces.repo.MainRepository
import ru.alek.hammerfood.domain.model.ServerResponse

class MainRepoImpl(private val api: ApiService): MainRepository {
    override suspend fun getRecipes(): ServerResponse? {
        try {
            val r = api.loadRandomRecipes()
            if(r.isSuccessful){
                if(r.code() in 200..399){
                    return r.body()
                }else{
                    throw Exception("http code: ${r.code()}")
                }
            }else{
                throw Exception(r.errorBody().toString())
            }
        }catch(e: Exception){
            throw e
        }
    }
}