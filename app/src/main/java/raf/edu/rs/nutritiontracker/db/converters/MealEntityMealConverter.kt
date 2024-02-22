package raf.edu.rs.nutritiontracker.db.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import raf.edu.rs.nutritiontracker.db.entities.MealEntity
import raf.edu.rs.nutritiontracker.models.domain.Meal
import java.util.Date

class MealEntityMealConverter {
    companion object {
        fun fromMeal(value: Meal?): MealEntity? {
            if (value == null) {
                return null
            }

            return try {
                MealEntity(
                    value.idMeal.toInt(),
                    value.strMeal,
                    value.strDrinkAlternate,
                    value.strCategory,
                    value.strArea,
                    value.strInstructions,
                    value.strMealThumb,
                    value.strTags,
                    value.strYoutube,
                    value.strIngredients,
                    value.strMeasure,
                    value.strSource,
                    value.strImageSource,
                    value.strCreativeCommonsConfirmed,
                    value.dateModified?.toLong(),
                    Date().toString(),
                    "Breakfast"
                )
            } catch (e: Exception) {
                throw IllegalArgumentException("Error converting meal to entity", e)
            }
        }


        fun toMeal(value: MealEntity?): Meal? {
            if (value == null) {
                return null
            }

            return try {
                Meal(
                    value.idMeal.toString(),
                    value.strMeal,
                    value.strDrinkAlternate,
                    value.strCategory,
                    value.strArea,
                    value.strInstructions,
                    value.strMealThumb,
                    value.strTags,
                    value.strYoutube,
                    value.strIngredients,

                    value.strMeasure,
                    value.strSource,
                    value.strImageSource,
                    value.strCreativeCommonsConfirmed,
                    null

                )
            } catch (e: Exception) {
                throw IllegalArgumentException("Error converting entity to meal", e)
            }
        }


    }

}