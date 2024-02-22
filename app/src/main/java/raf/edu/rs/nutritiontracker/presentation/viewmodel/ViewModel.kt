package raf.edu.rs.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import raf.edu.rs.nutritiontracker.db.entities.Count
import raf.edu.rs.nutritiontracker.db.entities.MealEntity
import raf.edu.rs.nutritiontracker.models.domain.Area
import raf.edu.rs.nutritiontracker.models.domain.Category
import raf.edu.rs.nutritiontracker.models.domain.Filter
import raf.edu.rs.nutritiontracker.models.domain.Ingredient
import raf.edu.rs.nutritiontracker.models.domain.Meal
import raf.edu.rs.nutritiontracker.presentation.contracts.DBContract
import raf.edu.rs.nutritiontracker.presentation.contracts.MainContract
import raf.edu.rs.nutritiontracker.presentation.contracts.PlanContract
import raf.edu.rs.nutritiontracker.repository.CategoryRepository
import raf.edu.rs.nutritiontracker.repository.DBMealRepository
import raf.edu.rs.nutritiontracker.repository.PlanDb
import timber.log.Timber

class ViewModel(
    private val categoryRepository: CategoryRepository,
    private val dbMealRepository: DBMealRepository
) : ViewModel(),
    MainContract.ViewModel,DBContract.ViewModel, PlanContract.ViewModel{


    override val categories: MutableLiveData<List<Category>> = MutableLiveData()
    override val areas: MutableLiveData<List<Area>> = MutableLiveData()

    override val ingredients: MutableLiveData<List<Ingredient>> = MutableLiveData()
    override val filteredList = MutableLiveData<List<Any>>()
    override val mealList: MutableLiveData<List<Meal>> = MutableLiveData();
    override val mealListFilteredByName: MutableLiveData<List<Meal>> = MutableLiveData();
    override val singleMeal:MutableLiveData<Meal> = MutableLiveData();

    override val dbmealListFilteredByName: MutableLiveData<List<MealEntity>> = MutableLiveData();
    override val singleMealDb:MutableLiveData<MealEntity> = MutableLiveData();
    override  val dbMealList:MutableLiveData<List<MealEntity>> = MutableLiveData()
    override var message:MutableLiveData<String> =MutableLiveData();
    override var days7cound:MutableLiveData<List<Count>> =MutableLiveData();


    override var planForDay: MutableLiveData<Map<String, Meal>> =MutableLiveData();
    override var plan: MutableLiveData<Map<String, Map<String, Meal>>> =MutableLiveData();
    override var selectedDay: MutableLiveData<String?> = MutableLiveData()
    override var selectedMeal: MutableLiveData<Meal?> = MutableLiveData()
    override var selectedMealType: MutableLiveData<String?> = MutableLiveData()
    override fun setSelectedMeal(meal: Meal?) {

        selectedMeal.value = meal;
        println(selectedMeal.value)
        PlanDb.selectedMeal=meal;
    }
    override fun getSelectedMeal() :Meal? {
        selectedMeal.value=PlanDb.selectedMeal
        return PlanDb.selectedMeal;
    }

    override fun setSelectedDay(day: String?) {
        selectedDay.value = day;
        println(selectedMeal.value)
        PlanDb.selectedDay=day;
    }

    override fun getSelectedDay(): String? {
        selectedDay.value=PlanDb.selectedDay
        return PlanDb.selectedDay;
    }
    override fun setSelectedMealType(day: String?) {
        selectedMealType.value = day;

        PlanDb.mealType=day;
    }

    override fun getSelectedMealType(): String? {
        selectedMealType.value=PlanDb.mealType
        return PlanDb.mealType;
    }

    override fun putMealInDay(day:String,mealType: String, meal: Meal) {
        var map= PlanDb.plan.getOrPut(day){
            mutableMapOf()
        }.getOrPut(mealType) {
            meal
        }
        plan.value=PlanDb.plan
        println(PlanDb.plan)

    }



    override fun getWeekPlan(): LiveData<Map<String, Map<String, Meal>>> {
        plan.value=PlanDb.plan;
        println("XCXD")
        println(PlanDb.plan)
        return plan;
    }


    private val subscriptions = CompositeDisposable()




    override fun getCategories() {

        val subscription = categoryRepository
            .allCateogries
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    categories.value = it.sortedBy { it1->it1.strCategory }

                    println(it)

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAreas() {
        val subscription = categoryRepository
            .allAreas
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    areas.value = it
                    println(it)

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getIngredients() {
        val subscription = categoryRepository
            .allIngredients
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    ingredients.value = it.sortedBy { it1 -> it1.strIngredient }
                    println(it)

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAndFilterBy(checked: Boolean, query: CharSequence, type: Int) {

        if(type==0){
            //categories
            filteredList.value= categories.value?.filter { it.strCategory?.contains(query,true) == true }
            if (checked)
                filteredList.value= filteredList.value?.asReversed();
        }
        if(type==1){
            //areas
            filteredList.value= areas.value?.filter { it.strArea.contains(query,true) }
            if (checked)
                filteredList.value= filteredList.value?.asReversed();
        }
        if(type==2){
            //ingredients
            filteredList.value=ingredients.value?.filter { it.strIngredient.contains(query,true) }
            if (checked)
                filteredList.value= filteredList.value?.asReversed();
        }

    }




    override fun getMealsByFilter(type: Filter, param:String){
        val subscription = categoryRepository
            .filterAndGetMeals(type,param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealList.value = it
                    mealListFilteredByName.value=it
                    println(it)

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)
    }



    override fun getMealsByName(query: CharSequence?) {

        if(query.isNullOrBlank() || query.isEmpty()){

            mealListFilteredByName.value=mealList.value;
            return;
        }
        val subscription = categoryRepository
            .filterAndGetMealsByName(query as String?)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealListFilteredByName.value=it
                    println(it);

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)


    }

    override fun getMealById(id: String) {


        val subscription = categoryRepository
            .getMealById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    singleMeal.value= it[0]
                    println(it)

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)


    }

    override fun insertMeal(mealEntity: MealEntity,callback: DBContract.InsertMealCallback) {

        val subscription = dbMealRepository
            .insertMeal(mealEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("COMPLETE INSERT")
                    callback.onMealInserted()

                },
                {
                    Timber.e(it)
                    callback.onInsertError(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteMeal(mealEntity: MealEntity, callback: DBContract.InsertMealCallback) {
        val subscription = dbMealRepository
            .deleteMeal(mealEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("COMPLETE")
                    callback.onMealInserted()
                },
                {
                    Timber.e(it)
                    callback.onInsertError(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMealsDB() {
        val subscription = dbMealRepository
            .getAllMeals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    dbMealList.value=it;
                    dbmealListFilteredByName.value=it;

                    Timber.e("COMPLETE")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsDBByFilter(type: Filter, param:String){
        val subscription = dbMealRepository
            .filterAndGetMeals(type,param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    dbMealList.value = it
                    dbmealListFilteredByName.value=it

                    println(it)

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)
    }



    override fun getMealsDBByName(query: CharSequence?) {

        if(query.isNullOrBlank() || query.isEmpty()){

            dbmealListFilteredByName.value=dbMealList.value;

            return;
        }
        val subscription = dbMealRepository
            .filterAndGetMealsByName(query as String?)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    dbmealListFilteredByName.value=it
                    println(it)

                },
                {
                    Timber.e(it)
                },
                {
                    Timber.e("Completed")
                }
            )
        subscriptions.add(subscription)


    }




    override fun updateMeal(mealEntity: MealEntity, callback: DBContract.InsertMealCallback) {
        val subscription = dbMealRepository
            .updateMeal(mealEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("COMPLETE")
                    callback.onMealInserted()
                },
                {
                    Timber.e(it)
                    callback.onInsertError(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealById(id: Int) {
        val subscription = dbMealRepository
            .getMealById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    singleMealDb.value=it
                    Timber.e("COMPLETE")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun get7dayCount() {
        val subscription = dbMealRepository
            .get7dayCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    days7cound.value=it;
                    Timber.e("COMPLETE7DAYS")
                    println(it)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


}