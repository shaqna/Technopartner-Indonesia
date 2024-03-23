package com.maou.myapplicationtest.presentation.dashboard.menu.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maou.myapplicationtest.R
import com.maou.myapplicationtest.databinding.CategoryItemBinding

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val categoryList = arrayListOf<String>()

    private var onItemSelectedListener: OnItemSelectedListener? = null
    private var categorySelected = 0

    fun setOnItemCallback(onItemSelected: (selectedCategory: String, position: Int) -> Unit) {
        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(category: String, position: Int) {
                onItemSelected(category, position)
            }
        }
    }

    fun setList(list: List<String>) {
        categoryList.clear()
        categoryList.addAll(list)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position], position)

        // Jika tidak di klik, maka text warna abu-abu dan indicator view hilang
        // jika di klik maka text warna hitam dan indicator view muncul berwarna hitam

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class ViewHolder(private val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, position: Int) {
            itemView.setOnClickListener {
                categorySelected = position
                onItemSelectedListener?.onItemSelected(category, position)

                notifyDataSetChanged()
            }

            if(categorySelected == position) {
                binding.tvCategory.setTextColor(Color.BLACK)
                binding.indicator.visibility = View.VISIBLE
            } else {
                binding.tvCategory.setTextColor(Color.GRAY)
                binding.indicator.visibility = View.GONE
            }

            binding.tvCategory.text = category
        }
    }
}

interface OnItemSelectedListener {
    fun onItemSelected(category: String, position: Int)
}