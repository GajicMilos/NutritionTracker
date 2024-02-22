package raf.edu.rs.nutritiontracker.presentation.contracts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import raf.edu.rs.nutritiontracker.models.domain.Area
import raf.edu.rs.nutritiontracker.models.domain.Category
import raf.edu.rs.nutritiontracker.models.domain.Filter
import raf.edu.rs.nutritiontracker.models.domain.Ingredient
import raf.edu.rs.nutritiontracker.models.domain.Meal

interface MainContract {

    interface ViewModel {
        val categories: LiveData<List<Category>>
        val areas:LiveData<List<Area>>
        val ingredients:LiveData<List<Ingredient>>
        fun getCategories()
        fun getAreas()
        fun getIngredients()
        fun getAndFilterBy(checked: Boolean, query: CharSequence, int: Int)




        val filteredList: MutableLiveData<List<Any>>
        val mealList: LiveData<List<Meal>>
        fun getMealsByFilter(type: Filter, param: String)
        fun getMealsByName(query: CharSequence?)
        val mealListFilteredByName: MutableLiveData<List<Meal>>
        val singleMeal: MutableLiveData<Meal>
        fun getMealById(id: String)
    }

}