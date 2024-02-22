package raf.edu.rs.nutritiontracker.presentation.contracts

import androidx.lifecycle.MutableLiveData
import raf.edu.rs.nutritiontracker.db.entities.Count
import raf.edu.rs.nutritiontracker.db.entities.MealEntity
import raf.edu.rs.nutritiontracker.models.domain.Filter


interface DBContract {

    interface ViewModel {
        fun insertMeal(mealEntity: MealEntity,callback: InsertMealCallback)
        fun deleteMeal(mealEntity: MealEntity,callback: InsertMealCallback)
        fun getAllMealsDB()
        fun updateMeal(mealEntity: MealEntity,callback: InsertMealCallback)
        fun getMealById(id:Int)
        var message:MutableLiveData<String>

        fun getMealsDBByName(query: CharSequence?)
        fun getMealsDBByFilter(type: Filter, param: String)
        val singleMealDb: MutableLiveData<MealEntity>
        val dbMealList: MutableLiveData<List<MealEntity>>
        val dbmealListFilteredByName: MutableLiveData<List<MealEntity>>
        fun get7dayCount()
        var days7cound: MutableLiveData<List<Count>>
    }
    interface InsertMealCallback {
        fun onMealInserted()
        fun onInsertError(error: Throwable)
    }
}