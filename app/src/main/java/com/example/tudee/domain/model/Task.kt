package com.example.tudee.domain.model

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val categoryIcon : String,  // saved here as a String even it is drawable or uri
    val priority: Int,
    val status: String,
)