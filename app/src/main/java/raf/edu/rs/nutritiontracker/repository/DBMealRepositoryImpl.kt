package raf.edu.rs.nutritiontracker.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import raf.edu.rs.nutritiontracker.db.dao.MealDao
import raf.edu.rs.nutritiontracker.db.entities.Count
import raf.edu.rs.nutritiontracker.db.entities.MealEntity
import raf.edu.rs.nutritiontracker.models.domain.Filter

class DBMealRepositoryImpl (private val mealDao: MealDao): DBMealRepository {
    override fun insertMeal(mealEntity: MealEntity): Completable {
        return mealDao.insertMeal(mealEntity)
    }

    override fun deleteMeal(mealEntity: MealEntity): Completable {
      return  mealDao.delete(mealEntity.idMeal);
    }

    override fun getAllMeals(): Observable<List<MealEntity>> {
        return mealDao.getAllMeals()
    }

    override fun updateMeal(mealEntity: MealEntity): Completable {
        return  mealDao.updateMeal(mealEntity);
    }

    override fun getMealById(id: Int): Single<MealEntity> {
        return mealDao.getMealById(id)
    }

    override fun filterAndGetMealsByName(query: String?): Observable<List<MealEntity>> {
        return mealDao.filterAndGetMealsByName(query)
    }

    override fun filterAndGetMeals(type: Filter, param: String): Observable<List<MealEntity>> {
       var filterType:String="";
        when(type){
            Filter.CATEGORY -> filterType="strCategory"
            Filter.AREA -> filterType="strArea"
            Filter.INGREDIENT -> filterType="strIngredient"
        }
        return mealDao.filterAndGetMeals(filterType,param)
    }

    override fun get7dayCount(): Observable<List<Count>> {
        return mealDao.get7dayCount()
    }



}