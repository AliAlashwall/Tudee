package com.example.tudee.presentation.screens.category

import com.example.tudee.R


data class Category(
    val name: String,
    val icon: Int
)

object TudeeCategories {
    val categoriesList: List<Category> = listOf(
        Category(name = "Education", icon = R.drawable.ic_book_open),
        Category(name = "Shopping", icon = R.drawable.ic_shopping_cart),
        Category(name = "Medical", icon = R.drawable.ic_hospital_location),
        Category(name = "Jym", icon = R.drawable.ic_body_part_muscle),
        Category(name = "Entertainment", icon = R.drawable.ic_baseball_bat),
        Category(name = "Cooking", icon = R.drawable.ic_chef),
        Category(name = "family & friend", icon = R.drawable.ic_user_multiple),
        Category(name = "traveling", icon = R.drawable.ic_airplane),
        Category(name = "agriculture", icon = R.drawable.ic_plant),
        Category(name = "coding", icon = R.drawable.ic_developer),
        Category(name = "adoration", icon = R.drawable.ic_quran),
        Category(name = "fixing bugs", icon = R.drawable.ic_bug),
        Category(name = "cleaning", icon = R.drawable.ic_blush_brush),
        Category(name = "work", icon = R.drawable.ic_money_bag),
        Category(name = "budgeting", icon = R.drawable.ic_money_bag),
        Category(name = "self - care", icon = R.drawable.ic_in_love),
        Category(name = "event", icon = R.drawable.ic_birthday_cake),
        Category(name = "Reading novels", icon = R.drawable.ic_book_open),
    )

}
