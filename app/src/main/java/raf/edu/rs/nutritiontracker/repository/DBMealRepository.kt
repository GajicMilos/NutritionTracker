package raf.edu.rs.nutritiontracker.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import raf.edu.rs.nutritiontracker.db.entities.Count
import raf.edu.rs.nutritiontracker.db.entities.MealEntity
import raf.edu.rs.nutritiontracker.models.domain.Filter

interface DBMealRepository {


    fun insertMeal(mealEntity: MealEntity): Completable
    fun deleteMeal(mealEntity: MealEntity) :Completable
    fun getAllMeals() : Observable<List<MealEntity>>
    fun updateMeal(mealEntity: MealEntity) :Completable
    fun getMealById(id:Int) :Single<MealEntity>
    abstract fun filterAndGetMealsByName(query: String?):Observable<List<MealEntity>>
    fun filterAndGetMeals(type: Filter, param: String): Observable<List<MealEntity>>


    fun get7dayCount(): Observable<List<Count>>
}