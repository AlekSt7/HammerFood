package ru.alek.hammerfood.presentation.home_screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.alek.hammerfood.R
import ru.alek.hammerfood.databinding.HomeScreenFragmentBinding
import ru.alek.hammerfood.domain.model.Product
import ru.alek.hammerfood.presentation.home_screen.adapter.BannerAdapter
import ru.alek.hammerfood.presentation.home_screen.adapter.ProductAdapter

class HomeScreen : Fragment() {

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var binding: HomeScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeScreenFragmentBinding.inflate(inflater)
        binding.retryButton.setOnClickListener {
            viewModel.fetchRecipes()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        observeData()
        viewModel.fetchBanners()
        viewModel.fetchRecipes()
    }

    private fun observeData(){
        viewModel.bannersLiveData.observe(viewLifecycleOwner){
            bindBanners(it)
        }
        viewModel.productsLiveData.observe(viewLifecycleOwner){
            bindProducts(it)
        }
        viewModel.uiStateLiveData.observe(viewLifecycleOwner){
            setUiState(it)
        }
    }

    private fun bindBanners(images: Array<Int>){
        binding.pizzaBanners.adapter = BannerAdapter(images)
    }

    private fun bindProducts(products: List<Product>){
        binding.foodContent.adapter = ProductAdapter(products)
    }

    private fun setUiState(uiState: Byte){
        binding.apply {
            when(uiState){
                HomeScreenViewModel.LOADING ->{
                    foodContent.visibility = View.GONE
                    errorWrapper.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                HomeScreenViewModel.LOADED ->{
                    progressBar.visibility = View.GONE
                    errorWrapper.visibility = View.GONE
                    foodContent.visibility = View.VISIBLE
                }
                HomeScreenViewModel.ERROR ->{
                    progressBar.visibility = View.GONE
                    foodContent.visibility = View.GONE
                    errorWrapper.visibility = View.VISIBLE
                }
            }
        }
    }

}