package com.maou.myapplicationtest.model

import com.squareup.moshi.Json

data class CategoryMenu(
    @field:Json(name ="category_name")
    val categoryName: String,
    @field:Json(name = "menu")
    val listDish: List<Dish>
)
