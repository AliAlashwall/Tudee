package com.example.tudee.data.local.database.mapper

import com.example.tudee.data.local.database.entity.CategoryEntity
import com.example.tudee.domain.model.Category


fun CategoryEntity.toDomain(): Category = Category(
    id = this.id,
    name = this.name,
    icon = this.icon,
    uriImage = this.uriImage,
    count = this.count,
    isCustom = this.isCustom
)


fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    icon = icon,
    uriImage = uriImage,
    count = count,
    isCustom = isCustom
)