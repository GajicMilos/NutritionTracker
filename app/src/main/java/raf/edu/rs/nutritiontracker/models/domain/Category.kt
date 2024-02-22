package raf.edu.rs.nutritiontracker.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    var idCategory: Int? = null,
    var strCategory: String? = null,
    var strCategoryThumb: String? = null,
    var strCategoryDescription: String? = null,
)
