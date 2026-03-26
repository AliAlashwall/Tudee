package com.example.tudee.database.mapper

import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.domain.model.Task

// Entity → Domain: used when reading from DB and sending up to the domain/presentation layer
fun TasksEntity.toDomain(): Task = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    date = this.date,
    categoryIcon = this.categoryIcon,
    priority = this.priority,
    status = this.status
)

// Domain → Entity: used when writing fresh data (from network) into the DB
fun Task.toTaskEntity(): TasksEntity = TasksEntity(
    id = id,
    title =title,
    description = description,
    date = date,
    categoryIcon =categoryIcon,
    priority = priority,
    status = status
)
