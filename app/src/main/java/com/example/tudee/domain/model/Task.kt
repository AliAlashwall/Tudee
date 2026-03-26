package com.example.tudee.domain.model

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val categoryId : Int,
    val priority: Int,
    val status: String,
)