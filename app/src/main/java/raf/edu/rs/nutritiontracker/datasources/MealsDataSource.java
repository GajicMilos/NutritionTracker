package raf.edu.rs.nutritiontracker.datasources;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import io.reactivex.Observable;
import raf.edu.rs.nutritiontracker.models.api.AreaResponse;
import raf.edu.rs.nutritiontracker.models.api.CategoriesResposne;
import raf.edu.rs.nutritiontracker.models.api.IngredientsResponse;
import raf.edu.rs.nutritiontracker.models.api.MealsResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MealsDataSource {


        @GET("https://www.themealdb.com/api/json/v1/1/categories.php")
        Observable<CategoriesResposne> getAllCategories();

        @GET("https://www.themealdb.com/api/json/v1/1/list.php?a=list")
        Observable<AreaResponse> getAllAreas();

        @GET("https://www.themealdb.com/api/json/v1/1/list.php?i=list")
        Observable<IngredientsResponse> getAllIngredients();

        @GET("https://www.themealdb.com/api/json/v1/1/filter.php")
        Observable<MealsResponse> getAndFilterMeals(@QueryMap Map<String,String> options);

        @GET("https://www.themealdb.com/api/json/v1/1/search.php")
        @NotNull Observable<MealsResponse> getAndFilterMealsByName(@Nullable @Query("s") String query);

        @GET("https://www.themealdb.com/api/json/v1/1/lookup.php")
        Observable<MealsResponse>   getMealById(@Query("i") String id);


}
