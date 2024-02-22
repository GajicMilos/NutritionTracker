package raf.edu.rs.nutritiontracker.repository;



import java.util.List;

import io.reactivex.Observable;
import raf.edu.rs.nutritiontracker.models.domain.Area;
import raf.edu.rs.nutritiontracker.models.domain.Category;
import raf.edu.rs.nutritiontracker.models.domain.Filter;
import raf.edu.rs.nutritiontracker.models.domain.Ingredient;
import raf.edu.rs.nutritiontracker.models.domain.Meal;

public interface CategoryRepository {

    Observable<List<Category>> getAllCateogries();
    Observable<List<Ingredient>> getAllIngredients();
    Observable<List<Area>> getAllAreas();

    Observable<List<Meal>> filterAndGetMeals(Filter type, String param) ;
    Observable<List<Meal>> filterAndGetMealsByName(String query) ;
    Observable<List<Meal>> getMealById(String id) ;
}
