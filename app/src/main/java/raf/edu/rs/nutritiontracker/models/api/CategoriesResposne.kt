package raf.edu.rs.nutritiontracker.models.api

import com.squareup.moshi.JsonClass
import raf.edu.rs.nutritiontracker.models.domain.Category
@JsonClass(generateAdapter = true)
data class CategoriesResposne(
    val categories:List<CategoryResponse>
)
