package ru.alek.hammerfood.domain.model

import com.google.gson.annotations.SerializedName

data class ServerResponse(@SerializedName("recipes") val products: List<Product>)