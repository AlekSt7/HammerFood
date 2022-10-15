package ru.alek.hammerfood.domain.interfaces.repo

import ru.alek.hammerfood.domain.model.ServerResponse

interface MainRepository {

    suspend fun getRecipes(): ServerResponse?

}