package com.example.tudee.database.mapper

import com.example.tudee.database.entity.CategoryEntity
import com.example.tudee.domain.model.Category


fun CategoryEntity.toDomain(): Category = Category(
    name = this.name,
    icon = this.icon,
    uriImage = this.uriImage,
    count = this.count,
    isCustom =this.isCustom
)


fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    icon = icon,
    uriImage = uriImage,
    count = count,
    isCustom = isCustom
)