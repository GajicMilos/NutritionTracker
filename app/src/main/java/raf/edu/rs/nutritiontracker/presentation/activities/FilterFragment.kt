package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.CompoundButton
import android.widget.SearchView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.adapters.AreasAdapter
import raf.edu.rs.nutritiontracker.adapters.CategoriesAdapter
import raf.edu.rs.nutritiontracker.adapters.IngredientsAdapter
import raf.edu.rs.nutritiontracker.adapters.OnFilterItemClickListener
import raf.edu.rs.nutritiontracker.models.domain.Area
import raf.edu.rs.nutritiontracker.models.domain.Category
import raf.edu.rs.nutritiontracker.models.domain.Filter
import raf.edu.rs.nutritiontracker.models.domain.Ingredient
import raf.edu.rs.nutritiontracker.presentation.contracts.MainContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel

class FilterFragment  : Fragment(R.layout.filter_fragment), OnFilterItemClickListener {

    private val postsViewModel: MainContract.ViewModel by viewModel<ViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel.getCategories()
        postsViewModel.getAreas()
        postsViewModel.getIngredients()
        initListeners(view);
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayout);
        recyclerView = view.findViewById<RecyclerView>(R.id.rvmFilter)
        recyclerView.layoutManager=LinearLayoutManager(view.context)
        tabLayout.selectTab(tabLayout.getTabAt(0));
        when (tabLayout.selectedTabPosition) {
            0 -> initCategoriesObserver()
            1 -> initAreasObserver()
            2 -> initIngredientsObserver()
        }


        Handler(Looper.getMainLooper()).postDelayed({
            postsViewModel.getAndFilterBy(false, "", tabLayout.selectedTabPosition)
            }, 100)

    }


    private fun initAreasObserver() = with(postsViewModel) {
        filteredList.observe(viewLifecycleOwner) {
            val adapter = AreasAdapter(it as List<Area>?)
            adapter.setOnItemClickListener(this@FilterFragment)
            recyclerView.adapter = adapter
        }
    }
    private fun initCategoriesObserver() = with(postsViewModel) {
        filteredList.observe(viewLifecycleOwner) {
            val adapter = CategoriesAdapter(it as List<Category>?)
            adapter.setOnItemClickListener(this@FilterFragment)
            recyclerView.adapter = adapter
        }


    }
    private fun initIngredientsObserver() = with(postsViewModel) {
        filteredList.observe(viewLifecycleOwner) {
            val adapter = IngredientsAdapter(it as List<Ingredient>?)
            adapter.setOnItemClickListener(this@FilterFragment)
            recyclerView.adapter = adapter
        }


    }
    private fun initListeners(view:View){
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // Called when a tab is selected
                val position = tab.position
                // Perform actions based on the selected tab position
                when (position) {
                    0 -> {
                        // Tab 1 selected
                        // Perform actions for Tab 1
                        initCategoriesObserver()

                        postsViewModel.getAndFilterBy(false,"",0)
                    }

                    1 -> {
                        // Tab 2 selected
                        // Perform actions for Tab 2
                        initAreasObserver()

                        postsViewModel.getAndFilterBy(false,"",1)
                    }

                    2 -> {
                        initIngredientsObserver()

                        postsViewModel.getAndFilterBy(false,"",2)
                        // Tab 3 selected
                        // Perform actions for Tab 3
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Called when a tab is unselected
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {

                        initCategoriesObserver()

                        postsViewModel.getAndFilterBy(false, "", 0)
                    }

                    1 -> {

                        initAreasObserver()

                        postsViewModel.getAndFilterBy(false, "", 1)
                    }

                    2 -> {
                        initIngredientsObserver()

                        postsViewModel.getAndFilterBy(false, "", 2)

                    }
                }
            }

        })


        val toggleButton:ToggleButton=view.findViewById(R.id.toggleButton)
        val searchView:SearchView=view.findViewById(R.id.searchView2)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                postsViewModel.getAndFilterBy(
                    toggleButton.isChecked,
                    searchView.query,
                    tabLayout.selectedTabPosition
                )

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // called when user changes the text value
                // newText parameter contains the updated text value

                postsViewModel.getAndFilterBy(
                    toggleButton.isChecked,
                    searchView.query,
                    tabLayout.selectedTabPosition
                )
                return false
            }
        })


        toggleButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            //CALL
            postsViewModel.getAndFilterBy(
                toggleButton.isChecked,
                searchView.query,
                tabLayout.selectedTabPosition
            )

        })
    }

    override fun onItemClick(type: Filter, category: String) {
        var fragmentTransaction=parentFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.addFragmentFcv,MealListFragment(type,category))
        fragmentTransaction.addToBackStack("back");
        fragmentTransaction.commit()

    }

}