package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.adapters.CategoriesAdapter
import raf.edu.rs.nutritiontracker.adapters.MealAdapter
import raf.edu.rs.nutritiontracker.adapters.OnFilterItemClickListener
import raf.edu.rs.nutritiontracker.adapters.OnMealItemClickListener
import raf.edu.rs.nutritiontracker.models.domain.Filter
import raf.edu.rs.nutritiontracker.models.domain.Meal
import raf.edu.rs.nutritiontracker.presentation.contracts.MainContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel

class MainFragment : Fragment(R.layout.categories_fragment), OnFilterItemClickListener , OnMealItemClickListener {

    private val postsViewModel: MainContract.ViewModel by viewModel<ViewModel>()
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initMealsObserver()
        recyclerView= view.findViewById(R.id.recyclerView);
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        postsViewModel.getCategories()
        var searchView:SearchView=view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isNullOrBlank())
                    postsViewModel.getMealsByName(query)
                else
                    initObservers()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!newText.isNullOrBlank())
                    postsViewModel.getMealsByName(searchView.query.toString())
                else
                    initObservers()
                return false
            }
        })

    }

    private fun init() {
        initObservers()

    }
    private fun initMealsObserver() = with(postsViewModel) {
        mealListFilteredByName.observe(viewLifecycleOwner) {
            val adapter = MealAdapter(it as List<Meal>?)
            adapter.setOnItemClickListener(this@MainFragment)
            adapter.isMeal=true;
            recyclerView.adapter = adapter

        }


    }

    private fun initObservers() = with(postsViewModel) {
        categories.observe(viewLifecycleOwner) {

            val adapter = CategoriesAdapter(it)
            adapter.setOnItemClickListener(this@MainFragment)
            recyclerView.adapter = adapter


        }

    }

    override fun onItemClick(type: Filter, category: String) {
        var fragmentTransaction=parentFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.addFragmentFcv,MealListFragment(Filter.CATEGORY,category))
        fragmentTransaction.addToBackStack("back")
        fragmentTransaction.commit()
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
