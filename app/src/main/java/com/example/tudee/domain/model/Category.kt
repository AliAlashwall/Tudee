package com.example.tudee.domain.model

data class Category(
    val id: Int = 0,
    val name: String,
    val icon: Int? = null,
    val uriImage: String = "",
    val count: Int,
    val isCustom: Boolean = false
)
