package com.example.tudee.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tudee.data.local.database.dao.CategoryDao
import com.example.tudee.data.local.database.dao.TasksDao
import com.example.tudee.data.local.database.entity.CategoryEntity
import com.example.tudee.data.local.database.entity.TasksEntity

@Database(entities = [TasksEntity::class, CategoryEntity::class], version = 2, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao
    abstract fun categoryDao(): CategoryDao
}