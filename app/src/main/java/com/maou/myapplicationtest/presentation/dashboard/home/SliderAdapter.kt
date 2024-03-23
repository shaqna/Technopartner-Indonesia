package com.maou.myapplicationtest.presentation.dashboard.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.maou.myapplicationtest.databinding.SliderItemBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val sliderList: List<String>): SliderViewAdapter<SliderAdapter.ViewHolder>() {



    override fun getCount(): Int {
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val itemBinding = SliderItemBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        viewHolder?.bind(sliderList[position])
    }

    inner class ViewHolder(private val binding: SliderItemBinding): SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(binding.ivSliderItem)
        }
    }
}