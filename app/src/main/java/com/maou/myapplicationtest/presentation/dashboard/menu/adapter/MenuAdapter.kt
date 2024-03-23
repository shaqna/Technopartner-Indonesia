package com.maou.myapplicationtest.presentation.dashboard.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maou.myapplicationtest.databinding.MenuItemBinding
import com.maou.myapplicationtest.model.CategoryMenu

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val listMenu = arrayListOf<CategoryMenu>()

    fun setList(list: List<CategoryMenu>) {
        listMenu.clear()
        listMenu.addAll(list)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val itemBinding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {

       holder.bind(listMenu[position])
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    inner class ViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private val dishAdapter: DishAdapter by lazy {
                DishAdapter()
            }

        fun bind(menu: CategoryMenu) {
            with(binding) {
                tvCategoryName.text = menu.categoryName
                setDishAdapter()
                dishAdapter.setList(menu.listDish)
            }
        }

        private fun setDishAdapter() {
            binding.rvDish.apply {
                adapter = dishAdapter
                layoutManager = LinearLayoutManager(itemView.context)
            }
        }
    }
}