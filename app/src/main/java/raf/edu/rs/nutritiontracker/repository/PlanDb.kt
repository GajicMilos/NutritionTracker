package raf.edu.rs.nutritiontracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import raf.edu.rs.nutritiontracker.models.domain.Meal

object PlanDb  {
    var selectedMeal:Meal?=null
    var selectedDay:String?=null
    var planForDay:MutableMap<String, Meal> = mutableMapOf()
    var plan: MutableMap<String, MutableMap<String, Meal>> = mutableMapOf()
    var mealType:String?=null
}