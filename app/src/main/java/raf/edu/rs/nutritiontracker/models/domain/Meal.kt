package raf.edu.rs.nutritiontracker.models.domain

data class Meal (
    var idMeal: String,
    var    strMeal: String,
    var    strDrinkAlternate: String?,
    var   strCategory: String?,
    var   strArea: String?,
    var   strInstructions: String?,
    var  strMealThumb: String?,
    var  strTags: String?,
    var  strYoutube: String?,
    var strIngredients:Map<String,String>?,
    var strMeasure:Map<String,String>?,
    var    strSource: String?,
    var    strImageSource: String?,
    var    strCreativeCommonsConfirmed: String?,
    var   dateModified: String?,
    ){
    override fun toString(): String {
        return "$strMeal";
    }
}