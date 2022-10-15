package ru.alek.hammerfood.presentation.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alek.hammerfood.App
import ru.alek.hammerfood.R
import ru.alek.hammerfood.data.network.repos.MainRepoImpl
import ru.alek.hammerfood.domain.GetProductsUseCase
import ru.alek.hammerfood.domain.model.Product
import java.lang.Exception

class HomeScreenViewModel : ViewModel() {

    private val api = App.instance.getApi()
    private val repo = MainRepoImpl(api)
    private val getProductsUseCase = GetProductsUseCase(repo)

    companion object {
        const val LOADING: Byte = 1
        const val LOADED: Byte = 2
        const val ERROR: Byte = 3
    }

    //LiveData
    private val bannersMutableLiveData = MutableLiveData<Array<Int>>()
    val bannersLiveData: LiveData<Array<Int>> = bannersMutableLiveData

    private val productsMutableLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> = productsMutableLiveData

    private val uiStateMutableLiveData = MutableLiveData(LOADING)
    val uiStateLiveData = uiStateMutableLiveData

    //Захардкодил картинки напрямую
    fun fetchBanners(){
        val banners = arrayOf(
            R.drawable.banner_1,
            R.drawable.banner_2,
            R.drawable.banner_3)
        bannersMutableLiveData.value = banners
    }

    fun fetchRecipes(){
        uiStateMutableLiveData.value = LOADING
        viewModelScope.launch {
            try {
                productsMutableLiveData.postValue(getProductsUseCase.execute()?.products)
                uiStateMutableLiveData.postValue(LOADED)
            }catch(e: Exception){
                uiStateMutableLiveData.postValue(ERROR)
            }
        }
    }

}