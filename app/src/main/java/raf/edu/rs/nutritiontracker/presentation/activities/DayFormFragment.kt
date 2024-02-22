package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.presentation.contracts.PlanContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel

class DayFormFragment : Fragment(R.layout.day_form_fragment) {
    private val planContract: PlanContract.ViewModel by viewModel<ViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonMonday: Button =view.findViewById(R.id.dayFormMondayButton)
        var buttonTuesday: Button =view.findViewById(R.id.dayFormTuesdayButton)
        var buttonWednesday: Button =view.findViewById(R.id.dayFormWednesdayButton)
        var buttonThursday: Button =view.findViewById(R.id.dayFormThursdayButton)
        var buttonFriday: Button =view.findViewById(R.id.dayFormFridayButton)
        var buttonSaturday: Button =view.findViewById(R.id.dayFormSaturdayButton)
        var buttonSunday: Button =view.findViewById(R.id.dayFormSundayButton)
        var buttonProceed:Button=view.findViewById(R.id.dayFormProccedButton)

        buttonMonday.setOnClickListener {  onDayButtonClickListener("monday")}
        buttonTuesday.setOnClickListener { onDayButtonClickListener("tuesday") }
        buttonWednesday.setOnClickListener { onDayButtonClickListener("wednesday") }
        buttonThursday.setOnClickListener {onDayButtonClickListener("thursday")  }
        buttonFriday.setOnClickListener {onDayButtonClickListener("friday")  }
        buttonSaturday.setOnClickListener {onDayButtonClickListener("saturday")  }
        buttonSunday.setOnClickListener {onDayButtonClickListener("sunday")  }


        buttonProceed.setOnClickListener {
            var fragmentTransaction=parentFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.addFragmentFcv,CheckPlanFragment())
            fragmentTransaction.addToBackStack("back4")
            fragmentTransaction.commit()
        }
    }

    fun onDayButtonClickListener(day:String){
        var fragmentTransaction=parentFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.addFragmentFcv,PlanByDayFragment(day))
        fragmentTransaction.addToBackStack("back3")
        fragmentTransaction.commit()
    }
}