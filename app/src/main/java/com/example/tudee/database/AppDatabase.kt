package com.example.tudee.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tudee.database.dao.TasksDao
import com.example.tudee.database.entity.TasksEntity

@Database(entities = [TasksEntity::class], version = 2, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =

            INSTANCE ?: synchronized(this) {

                Room.databaseBuilder(context, AppDatabase::class.java, "db")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}