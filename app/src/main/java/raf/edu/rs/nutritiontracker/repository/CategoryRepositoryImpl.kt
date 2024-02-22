package raf.edu.rs.nutritiontracker.repository

import io.reactivex.Observable
import raf.edu.rs.nutritiontracker.datasources.MealsDataSource
import raf.edu.rs.nutritiontracker.models.domain.Area
import raf.edu.rs.nutritiontracker.models.domain.Category
import raf.edu.rs.nutritiontracker.models.domain.Filter
import raf.edu.rs.nutritiontracker.models.domain.Ingredient
import raf.edu.rs.nutritiontracker.models.domain.Meal

class CategoryRepositoryImpl
    (private val mealsDataSource: MealsDataSource
) : CategoryRepository {


    override fun getAllCateogries(): Observable<List<Category>> {


        return mealsDataSource
            .allCategories
            .map {
                it.categories.map { categoryResponse ->
                    Category(
                        idCategory = categoryResponse.idCategory,
                        strCategory = categoryResponse.strCategory,
                        strCategoryThumb = categoryResponse.strCategoryThumb,
                        strCategoryDescription = categoryResponse.strCategoryDescription
                    )
                }
            }
    }

    override fun getAllIngredients(): Observable<List<Ingredient>> {
        return mealsDataSource
            .allIngredients
            .map {
                it.meals.map { ingredient ->
                    Ingredient(
                        strDescription = ingredient.strDescription,
                        strIngredient = ingredient.strIngredient,
                        strType = ingredient.strType,
                        idIngredient = ingredient.idIngredient
                    )
                }
            }
    }

    override fun getAllAreas(): Observable<List<Area>> {
        return mealsDataSource
            .allAreas
            .map {
                it.meals.map { area ->
                    Area(
                        strArea = area.strArea,

                    )
                }
            }
    }
    override fun filterAndGetMeals(type:Filter, param:String): Observable<List<Meal>> {
      val map=  HashMap<String?, String?>()
        map[type.type.toString()] = param;
        return mealsDataSource
            .getAndFilterMeals(map)
            .map {
                it.meals?.map { meal ->
                    var mapIngredient= HashMap<String,String>()
                    var mapMeasure=HashMap<String,String>()
                   for (i in 1..20){
                       val ingredientField = "strIngredient$i"
                       mapIngredient[ingredientField] = meal["strIngredient$i"]  ?: ""
                       mapMeasure["strMeasure$i"]=meal["strMeasure$i"]?:""
                   }

                    Meal(
                        idMeal = meal.idMeal,
                        strMeal = meal.strMeal,
                        strDrinkAlternate = meal.strDrinkAlternate,
                        strCategory = meal.strCategory,
                        strArea = meal.strArea,
                        strInstructions = meal.strInstructions,
                        strMealThumb = meal.strMealThumb,
                        strTags = meal.strTags,
                        strYoutube = meal.strYoutube,
                        strSource = meal.strSource,
                        strImageSource = meal.strImageSource,
                        strCreativeCommonsConfirmed = meal.strCreativeCommonsConfirmed,
                        dateModified = meal.dateModified,
                        strIngredients = mapIngredient,
                        strMeasure = mapMeasure
                    )

                }
            }
    }

    override fun filterAndGetMealsByName(query: String?): Observable<List<Meal>> {
        return mealsDataSource
            .getAndFilterMealsByName(query)
            .map {
                it.meals?.map { meal ->
                    var mapIngredient= HashMap<String,String>()
                    var mapMeasure=HashMap<String,String>()
                    for (i in 1..20){
                        val ingredientField = "strIngredient$i"
                        mapIngredient[ingredientField] = meal["strIngredient$i"]  ?: ""
                        mapMeasure["strMeasure$i"]=meal["strMeasure$i"]?:""
                    }

                    Meal(

                        idMeal = meal.idMeal,
                        strMeal = meal.strMeal,
                        strDrinkAlternate = meal.strDrinkAlternate,
                        strCategory = meal.strCategory,
                        strArea = meal.strArea,
                        strInstructions = meal.strInstructions,
                        strMealThumb = meal.strMealThumb,
                        strTags = meal.strTags,
                        strYoutube = meal.strYoutube,
                        strSource = meal.strSource,
                        strImageSource = meal.strImageSource,
                        strCreativeCommonsConfirmed = meal.strCreativeCommonsConfirmed,
                        dateModified = meal.dateModified,
                        strIngredients = mapIngredient,
                        strMeasure = mapMeasure
                    )

                }?: emptyList();
            }
    }


    override fun getMealById(id: String): Observable<List<Meal>> {
        return mealsDataSource
            .getMealById(id)  .map {
                it.meals?.map { meal ->
                    var mapIngredient= HashMap<String,String>()
                    var mapMeasure=HashMap<String,String>()
                    for (i in 1..20){
                        val ingredientField = "strIngredient$i"
                        mapIngredient[ingredientField] = meal["strIngredient$i"]  ?: ""
                        mapMeasure["strMeasure$i"]=meal["strMeasure$i"]?:""
                    }

                    Meal(

                        idMeal = meal.idMeal,
                        strMeal = meal.strMeal,
                        strDrinkAlternate = meal.strDrinkAlternate,
                        strCategory = meal.strCategory,
                        strArea = meal.strArea,
                        strInstructions = meal.strInstructions,
                        strMealThumb = meal.strMealThumb,
                        strTags = meal.strTags,
                        strYoutube = meal.strYoutube,
                        strSource = meal.strSource,
                        strImageSource = meal.strImageSource,
                        strCreativeCommonsConfirmed = meal.strCreativeCommonsConfirmed,
                        dateModified = meal.dateModified,
                        strIngredients = mapIngredient,
                        strMeasure = mapMeasure
                    )

                }?: emptyList()
            }


    }

}