package raf.edu.rs.nutritiontracker.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient(
    var strIngredient: String,
    var idIngredient: Int,
    var strDescription: String?=null,
    var strType: String? = null,
)
