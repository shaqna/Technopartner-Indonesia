package com.maou.myapplicationtest.data.source.remote.response

import com.maou.myapplicationtest.model.CategoryMenu

data class MenuResponse(
    val status: String,
    val result: Categories
)

data class Categories(
    val categories: List<CategoryMenu>
)