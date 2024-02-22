package raf.edu.rs.nutritiontracker.presentation.contracts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import raf.edu.rs.nutritiontracker.models.domain.Area
import raf.edu.rs.nutritiontracker.models.domain.Category
import raf.edu.rs.nutritiontracker.models.domain.Meal

interface  PlanContract {
    interface ViewModel {
        var planForDay: MutableLiveData<Map<String, Meal>>
        var selectedMeal: MutableLiveData<Meal?>
        var plan:MutableLiveData<Map<String,Map<String,Meal>>>
        var selectedDay: MutableLiveData<String?>
        var selectedMealType: MutableLiveData<String?>
        fun setSelectedMeal(meal: Meal?)
        fun getSelectedMeal(): Meal?
        fun setSelectedDay(day: String?)
        fun getSelectedDay(): String?
        fun setSelectedMealType(day: String?)
        fun getSelectedMealType(): String?
        fun putMealInDay(day:String,mealType:String,meal: Meal)
        fun getWeekPlan() :LiveData<Map<String,Map<String,Meal>>>
    }
}
