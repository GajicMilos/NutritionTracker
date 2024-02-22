package raf.edu.rs.nutritiontracker.models.api

import com.squareup.moshi.JsonClass
import raf.edu.rs.nutritiontracker.models.domain.Ingredient

@JsonClass(generateAdapter = true)
data class IngredientsResponse(
    val meals:List<Ingredient>

)