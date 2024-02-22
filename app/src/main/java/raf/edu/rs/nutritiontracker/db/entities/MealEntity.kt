package raf.edu.rs.nutritiontracker.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import raf.edu.rs.nutritiontracker.db.converters.StringMapConverter

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
        var idMeal: Int,
    var    strMeal: String,
    var    strDrinkAlternate: String?,
    var   strCategory: String?,
    var   strArea: String?,
    var   strInstructions: String?,
    var  strMealThumb: String?,
    var  strTags: String?,
    var  strYoutube: String?,

    @TypeConverters(StringMapConverter::class)
        var strIngredients:Map<String,String>? = mapOf(),

    @TypeConverters(StringMapConverter::class)
        var strMeasure:Map<String,String>? = mapOf(),
    var    strSource: String?,
    var    strImageSource: String?,
    var    strCreativeCommonsConfirmed: String?,
    var   dateModified: Long?,
    var datePlanned:String?,
    var forMeal:String?
)