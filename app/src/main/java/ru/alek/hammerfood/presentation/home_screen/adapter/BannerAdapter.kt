package ru.alek.hammerfood.presentation.home_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alek.hammerfood.databinding.ToolbarItemBinding

class BannerAdapter(private val images: Array<Int>): RecyclerView.Adapter<BannerAdapter.BannerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BannerHolder(
        ToolbarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    class BannerHolder(private val binding: ToolbarItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image_r: Int){
            binding.banner.setImageResource(image_r)
        }
    }

}