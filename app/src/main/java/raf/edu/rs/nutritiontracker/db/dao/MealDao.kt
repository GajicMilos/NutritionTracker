package raf.edu.rs.nutritiontracker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import raf.edu.rs.nutritiontracker.db.entities.Count
import raf.edu.rs.nutritiontracker.db.entities.MealEntity

@Dao
abstract class MealDao {

    @Insert
    abstract fun insertMeal(mealEntity: MealEntity): Completable

    @Query("DELETE FROM meals")
    abstract fun deleteAll(): Completable

    @Query("DELETE  FROM meals where idMeal=:id")
    abstract fun delete( id:Int):Completable

    @Query("Select * from meals")
    abstract fun getAllMeals():Observable<List<MealEntity>>

    @Query("Select * FROM meals where idMeal=:id")
    abstract fun getMealById(id: Int):Single<MealEntity>

    @Update
    abstract fun updateMeal(mealEntity: MealEntity):Completable

    @Query("SELECT * FROM meals WHERE strMeal LIKE '%' || :query || '%'")
    abstract fun filterAndGetMealsByName(query: String?): Observable<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE " +
            "(:type = 'strCategory' AND strCategory = :param) OR " +
            "(:type = 'strArea' AND strArea = :param) OR " +
            "(:type = 'strIngredient' AND strIngredients LIKE '%'||:param||'%')")
    abstract fun filterAndGetMeals(type: String, param: String): Observable<List<MealEntity>>


    @Query("SELECT DATE(dateModified/1000,'unixepoch','localtime') AS day, COUNT(*) AS " +
            "mealCount FROM meals  WHERE day >= date('now', '-7 days') " +
            "GROUP BY day  ORDER BY day DESC;")
    abstract fun get7dayCount() :Observable<List<Count>>
}