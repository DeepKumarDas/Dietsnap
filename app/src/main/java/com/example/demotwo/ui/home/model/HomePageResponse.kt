package com.example.demotwo.ui.home.model

data class HomePageResponse(
    val success: Boolean,
    val data: HomePageData,
    val message: String,
)

data class HomePageData(
    val section_1: Section1,
    val section_2: Section2,
    val section_3: Section3,
    val section_4: Section4,
)

data class Section1(
    val plan_name: String,
    val progress: String,
)

data class Section2(
    val accuracy: String,
    val workout_duration: String,
    val reps: Long,
    val calories_burned: Long,
)

data class Section3(
    val plan_1: Plan1,
    val plan_2: Plan2,
)

data class Plan1(
    val plan_name: String,
    val difficulty: String,
)

data class Plan2(
    val plan_name: String,
    val difficulty: String,
)

data class Section4(
    val category_1: Category1,
    val category_2: Category2,
)

data class Category1(
    val category_name: String,
    val no_of_exercises: String,
)

data class Category2(
    val category_name: String,
    val no_of_exercises: String,
)


