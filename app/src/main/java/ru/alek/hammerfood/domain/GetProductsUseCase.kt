package ru.alek.hammerfood.domain

import ru.alek.hammerfood.domain.interfaces.repo.MainRepository

class GetProductsUseCase(private val repository: MainRepository) {

    suspend fun execute() = repository.getRecipes()

}