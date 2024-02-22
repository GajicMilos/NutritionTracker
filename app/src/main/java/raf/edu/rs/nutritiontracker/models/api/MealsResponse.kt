package raf.edu.rs.nutritiontracker.models.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealsResponse (
    val meals:List<MealResponse>?
    )
