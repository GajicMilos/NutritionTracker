package raf.edu.rs.nutritiontracker.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Area(
    var strArea: String
)
