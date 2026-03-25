package com.example.tudee.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class TasksEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val categoryIcon : String,  // saved here as a String even it is drawable or uri
    val priority: Int,
    val status: String,
)
