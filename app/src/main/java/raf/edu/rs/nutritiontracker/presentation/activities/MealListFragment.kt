package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.adapters.MealAdapter
import raf.edu.rs.nutritiontracker.adapters.OnMealItemClickListener
import raf.edu.rs.nutritiontracker.db.converters.MealEntityMealConverter
import raf.edu.rs.nutritiontracker.models.domain.Filter
import raf.edu.rs.nutritiontracker.models.domain.Meal
import raf.edu.rs.nutritiontracker.presentation.contracts.DBContract
import raf.edu.rs.nutritiontracker.presentation.contracts.MainContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel

class MealListFragment(val type: Filter,val param: String ) : Fragment(R.layout.meal_list_fragment), OnMealItemClickListener {

    private val postsViewModel: MainContract.ViewModel by viewModel<ViewModel>()
    private val dbViewModel: DBContract.ViewModel by viewModel<ViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var toggleButton:ToggleButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleButton=view.findViewById(R.id.toggleDB)
        postsViewModel.getMealsByFilter(type,param);
        dbViewModel.getMealsDBByFilter(type,param);
        initMealsObserver()
        initMealsDBObserver()
        initListeners(view);
        toggleButton.text="MEALS"
        toggleButton.textOff="MEALSDB"


        recyclerView = view.findViewById<RecyclerView>(R.id.mealListrvm)
        recyclerView.layoutManager = LinearLayoutManager(view.context)


    }


    private fun initMealsObserver() = with(postsViewModel) {
        mealListFilteredByName.observe(viewLifecycleOwner) {
            val adapter = MealAdapter(it as List<Meal>?)
            adapter.setOnItemClickListener(this@MealListFragment)
            adapter.isMeal=true;
            recyclerView.adapter = adapter
            timber.log.Timber.e("Got posts!")
        }


    }
    private fun initMealsDBObserver() = with(dbViewModel) {
        dbmealListFilteredByName.observe(viewLifecycleOwner) {
           var list= it.map { mealEntity->
                MealEntityMealConverter.toMeal(mealEntity)
            }
            val adapter = MealAdapter(list as List<Meal>?)
            adapter.setOnItemClickListener(this@MealListFragment)
            adapter.isMeal=false;
            recyclerView.adapter = adapter

        }


    }

    private fun initListeners(view: View) {

        val searchView: SearchView = view.findViewById(R.id.searchViewMealList)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                 if(toggleButton.isChecked)
                    postsViewModel.getMealsByName(query)
                 else
                     dbViewModel.getMealsDBByName(query)
                 println("OVO RADI")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // called when user changes the text value
                // newText parameter contains the updated text value
                println("OVO RADI")
                if(toggleButton.isChecked)
                    postsViewModel.getMealsByName(searchView.query.toString())
                else
                    dbViewModel.getMealsDBByName(searchView.query.toString())

                return false
            }
        })
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                initMealsObserver()
             //   toggleButton.textOn="MEALS"
                postsViewModel.getMealsByName(searchView.query.toString())
            } else {
               // toggleButton.textOff="MEALSDB"
                dbViewModel.getMealsDBByName(searchView.query.toString())
               initMealsDBObserver()
            }


        }

    }

    override fun onItemClickListener(meal: Meal, isMeal: Boolean) {
        var fragmentTransaction=parentFragmentManager.beginTransaction();
        if(isMeal)
            fragmentTransaction.replace(R.id.addFragmentFcv,MealFragment(meal))
        else
            fragmentTransaction.replace(R.id.addFragmentFcv,MealDBFragment(meal))
        fragmentTransaction.addToBackStack("back1")
        fragmentTransaction.commit()
    }

}