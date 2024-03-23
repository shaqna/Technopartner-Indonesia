package com.maou.myapplicationtest.presentation.dashboard.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maou.myapplicationtest.databinding.ItemDishBinding
import com.maou.myapplicationtest.model.Dish

class DishAdapter: RecyclerView.Adapter<DishAdapter.ViewHolder>() {

    private val listDish = arrayListOf<Dish>()

    fun setList(list: List<Dish>) {
        listDish.clear()
        listDish.addAll(list)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishAdapter.ViewHolder {
        val itemBinding = ItemDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DishAdapter.ViewHolder, position: Int) {
        holder.bind(listDish[position])
    }

    override fun getItemCount(): Int {
        return listDish.size
    }

    inner class ViewHolder(private val binding: ItemDishBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(dish: Dish) {
            if(adapterPosition == listDish.size -1) {
                binding.view3.visibility = View.GONE
            }
            with(binding) {
                tvDishName.text = dish.name
                tvDescription.text = dish.description
                tvPrice.text = dish.price.toString()

                Glide.with(itemView.context).load(dish.photo).into(ivDish)
            }
        }
    }
}