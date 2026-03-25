package com.example.tudee.presentation.screens.categories
//
//import com.example.tudee.R
//
//
//data class Category(
//    val name: String,
//    val icon: Any,
//    val count: Int = 0,
//    val isCustom: Boolean = false // Helper to distinguish custom icons
//)
//
//// we may need to create a category Entity, as we can't cleanly update categoriesList with new categories
//enum class Categories(val category: Category) {
//    EDUCATION(category = Category(name = "Education", icon = R.drawable.ic_book_open)),
//    SHOPPING(category = Category(name = "Shopping", icon = R.drawable.ic_shopping_cart)),
//    MEDICAL(category = Category(name = "Medical", icon = R.drawable.ic_hospital_location)),
//    JYM(category = Category(name = "Jym", icon = R.drawable.ic_body_part_muscle)),
//    ENTERTAINMENT(category = Category(name = "Entertainment", icon = R.drawable.ic_baseball_bat)),
//    COOKING(category = Category(name = "Cooking", icon = R.drawable.ic_chef)),
//    FAMILY_AND_FRIENDS(
//        category = Category(
//            name = "family & friend",
//            icon = R.drawable.ic_user_multiple
//        )
//    ),
//    TRAVELING(category = Category(name = "traveling", icon = R.drawable.ic_airplane)),
//    AGRICULTURE(category = Category(name = "agriculture", icon = R.drawable.ic_plant)),
//    CODING(category = Category(name = "coding", icon = R.drawable.ic_developer)),
//    ADORATION(category = Category(name = "adoration", icon = R.drawable.ic_quran)),
//    FIXING_BUGS(category = Category(name = "fixing bugs", icon = R.drawable.ic_bug)),
//    CLEANING(category = Category(name = "cleaning", icon = R.drawable.ic_blush_brush)),
//    WORK(category = Category(name = "work", icon = R.drawable.ic_money_bag)),
//    Budgeting(category = Category(name = "budgeting", icon = R.drawable.ic_money_bag)),
//    SELF_CARE(category = Category(name = "self - care", icon = R.drawable.ic_in_love)),
//    EVENT(category = Category(name = "event", icon = R.drawable.ic_birthday_cake)),
//    READING_NOVELS(category = Category(name = "Reading novels", icon = R.drawable.reading_novels)),
//
//}
//
//
//object TudeeCategories {
//    val categoriesList: List<Category> = listOf(
//        Categories.EDUCATION.category,
//        Categories.SHOPPING.category,
//        Categories.MEDICAL.category,
//        Categories.JYM.category,
//        Categories.ENTERTAINMENT.category,
//        Categories.COOKING.category,
//        Categories.FAMILY_AND_FRIENDS.category,
//        Categories.TRAVELING.category,
//        Categories.AGRICULTURE.category,
//        Categories.CODING.category,
//        Categories.ADORATION.category,
//        Categories.FIXING_BUGS.category,
//        Categories.CLEANING.category,
//        Categories.WORK.category,
//        Categories.Budgeting.category,
//        Categories.SELF_CARE.category,
//        Categories.EVENT.category,
//        Categories.READING_NOVELS.category,
//    )
//
//}
