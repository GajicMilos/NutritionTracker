package raf.edu.rs.nutritiontracker.models.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    var idCategory: Int? = null,
    var strCategory: String? = null,
    var strCategoryThumb: String? = null,
    var strCategoryDescription: String? = null,
)