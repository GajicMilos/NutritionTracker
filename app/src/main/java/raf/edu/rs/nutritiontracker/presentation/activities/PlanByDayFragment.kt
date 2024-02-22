package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.models.domain.Meal
import raf.edu.rs.nutritiontracker.presentation.contracts.DBContract
import raf.edu.rs.nutritiontracker.presentation.contracts.MainContract
import raf.edu.rs.nutritiontracker.presentation.contracts.PlanContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel

class PlanByDayFragment(var day: String) : Fragment(R.layout.create_plan_by_day_fragment) {
    private val postsViewModel: MainContract.ViewModel by viewModel<ViewModel>()
    private val mealDbViewModel: DBContract.ViewModel by viewModel<ViewModel>()
    private val planContract: PlanContract.ViewModel by viewModel<ViewModel>()
    open var selectedMeal:Meal?=null;
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var breakfastButton: Button =view.findViewById(R.id.dayBreakfastButton)
        var lunchButton: Button =view.findViewById(R.id.dayLunchButton)
        var dinnerButton: Button =view.findViewById(R.id.dayDinnerButton)
        var snackButton: Button =view.findViewById(R.id.daySnackButton)
        var breakfastTv: TextView =view.findViewById(R.id.dayBreakfastTv)
        var lunchTv: TextView =view.findViewById(R.id.dayLunchTv)
        var dinnerTv: TextView =view.findViewById(R.id.dayDinnerTv)
        var snackTv: TextView =view.findViewById(R.id.daySnackTv)
        var dayTv:TextView=view.findViewById(R.id.dayDayTv)
        var saveDay:Button=view.findViewById(R.id.daySaveButton)


        var map=    planContract.getWeekPlan().value;
        breakfastButton.setOnClickListener {  onMealByDayButtonClickListener("breakfast")}
        lunchButton.setOnClickListener { onMealByDayButtonClickListener("lunch") }
        dinnerButton.setOnClickListener { onMealByDayButtonClickListener("dinner") }
        snackButton.setOnClickListener {onMealByDayButtonClickListener("snack")  }

        planContract.plan.observe(viewLifecycleOwner) {
            breakfastTv.text= if(map?.get(day)?.get("breakfast")?.strMeal.isNullOrBlank() )  "choose Meal" else map?.get(day)?.get("breakfast")?.strMeal;
            lunchTv.text= if(map?.get(day)?.get("lunch")?.strMeal.isNullOrBlank() )  "choose Meal" else map?.get(day)?.get("lunch")?.strMeal;
            dinnerTv.text= if(map?.get(day)?.get("dinner")?.strMeal.isNullOrBlank() )  "choose Meal" else map?.get(day)?.get("dinner")?.strMeal;
            snackTv.text= if(map?.get(day)?.get("snack")?.strMeal.isNullOrBlank() )  "choose Meal" else map?.get(day)?.get("snack")?.strMeal;
            dayTv.text=day;
            println(map)
            println(map?.get(day)?.get("breakfast")?.strMeal)
            println(map?.get(day)?.get("lunch")?.strMeal)
            println(map?.get(day)?.get("dinner")?.strMeal)
            println(map?.get(day)?.get("snack")?.strMeal)








        }
        val selected=  planContract.getSelectedMeal()
        val mealType=   planContract.getSelectedMealType()
        if (selected!=null && !mealType.isNullOrBlank()){
            when(planContract.getSelectedMealType()){
                "breakfast"->{
                    breakfastTv.text=selected.strMeal
                    planContract.putMealInDay(day,mealType,selected)
                    planContract.setSelectedMeal(null)

                }
                "lunch"->{
                    lunchTv.text=selected.strMeal
                    planContract.putMealInDay(day,mealType,selected)
                    planContract.setSelectedMeal(null)
                }
                "dinner"->{
                    dinnerTv.text=selected.strMeal
                    planContract.putMealInDay(day,mealType,selected)
                    planContract.setSelectedMeal(null)
                }
                "snack"->{
                    snackTv.text=selected.strMeal
                    planContract.putMealInDay(day,mealType,selected)
                    planContract.setSelectedMeal(null)
                }
            }

        }


                 saveDay.setOnClickListener {

           // planContract.putDayPlanInWeek(planContract.getPlanForDay().value!!,day)



            parentFragmentManager.popBackStack()

        }


    }

    private fun onMealByDayButtonClickListener(day:String){
        planContract.setSelectedMealType(day)
        var fragmentTransaction=parentFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.addFragmentFcv,FilterFragment(),"PlanByDayFragment")
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addToBackStack("PlanByDayFragment")
        fragmentTransaction.commit()
    }



}
