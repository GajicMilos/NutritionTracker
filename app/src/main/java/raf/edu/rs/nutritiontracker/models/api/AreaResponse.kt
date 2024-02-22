package raf.edu.rs.nutritiontracker.models.api

import com.squareup.moshi.JsonClass
import raf.edu.rs.nutritiontracker.models.domain.Area

@JsonClass(generateAdapter = true)
data class AreaResponse(
    val meals:List<Area>
)