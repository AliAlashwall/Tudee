package com.example.tudee.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val icon: Int? = null,
    val uriImage: String = "",
    val count: Int,
    val isCustom: Boolean = false
)
