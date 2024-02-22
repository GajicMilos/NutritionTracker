package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.models.domain.Meal
import raf.edu.rs.nutritiontracker.presentation.contracts.MainContract
import raf.edu.rs.nutritiontracker.presentation.contracts.PlanContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel

class MealFragment(var mealx: Meal) : Fragment(R.layout.meal_fragment) {

    private val postsViewModel: MainContract.ViewModel by viewModel<ViewModel>()

    private val planContract: PlanContract.ViewModel by viewModel<ViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsViewModel.getMealById(mealx.idMeal)
        var button = view.findViewById<Button>(R.id.saveMealButton)

        var tvMealName = view.findViewById<TextView>(R.id.tvMealNameDB)
        var tvMealCategory = view.findViewById<TextView>(R.id.tvMealCategoryDB)
        var tvMealArea = view.findViewById<TextView>(R.id.tvMealAreaDB)
        var tvInstructions = view.findViewById<TextView>(R.id.tvInstructionsDB)
        var tvIngredientsVal = view.findViewById<TextView>(R.id.tvIngredientsValDB)
        var tvLink = view.findViewById<TextView>(R.id.tvLinkDB)
        var tvTags = view.findViewById<TextView>(R.id.tvTagsDB)
        var imageView = view.findViewById<ImageView>(R.id.mealDBImageView)


        var progressBar:ProgressBar=view.findViewById(R.id.progressBarMealFragment)
        var scrollView:ScrollView=view.findViewById(R.id.scv)
        progressBar.isVisible=true;
        scrollView.isVisible=false;

        postsViewModel.singleMeal.observe(viewLifecycleOwner) {

            var meal = it;
            button.setOnClickListener {
                var fragmentTransaction = parentFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addFragmentFcv, SaveMealFragment(meal))
                fragmentTransaction.addToBackStack("back2")
                fragmentTransaction.commit()
            }
            val fragmentTag = "PlanByDayFragment"
            val fragment = parentFragmentManager.findFragmentByTag(fragmentTag)
            val isFragmentOnBackStack = fragment != null && fragment.isInBackStack()

            if (isFragmentOnBackStack) {
                button.text = "add to plan"
                button.setOnClickListener {
                    planContract.setSelectedMeal(meal)

                    println("selectedMeal value in first fragment: ${planContract.selectedMeal.value}")

                    val fragment = parentFragmentManager.findFragmentByTag("PlanByDayFragment")
                    if (fragment is PlanByDayFragment) {
                        fragment.selectedMeal = meal;
                    }
                    parentFragmentManager.popBackStack(
                        "PlanByDayFragment",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )


                }
            }


            tvMealName.text = if (it?.strMeal == null) "Not Available" else it.strMeal
            tvMealArea.text = if (it?.strArea == null) "Not Available" else it.strArea
            tvMealCategory.text =
                if (it?.strCategory == null) "Not Available" else it.strCategory
            tvInstructions.text =
                if (it?.strInstructions == null) "Not Available" else it.strInstructions
            tvLink.text = if (it?.strYoutube == null) "Not Available" else it.strYoutube
            tvTags.text = if (it?.strTags == null) "Not Available" else it.strTags
            Glide.with(view.context)
                .load(if (it?.strImageSource == null) it?.strMealThumb else it.strImageSource)
                .into(imageView);
            var sb: StringBuilder = StringBuilder();
            for (i in 1..20) {
                if (it?.strIngredients?.get("strIngredient$i").isNullOrBlank())
                    break;
                sb.append(it?.strIngredients?.get("strIngredient$i"))
                sb.append(": ")
                sb.append(it?.strMeasure?.get("strMeasure$i"))
                sb.append("\n")
            }
            tvIngredientsVal.text = sb.toString()
            progressBar.isVisible=false;
            scrollView.isVisible=true;
        }


    }


    fun Fragment.isInBackStack(): Boolean {
        val backStackCount = parentFragmentManager.backStackEntryCount ?: 0
        for (i in 0 until backStackCount) {
            val backStackEntry = parentFragmentManager.getBackStackEntryAt(i)
            if (backStackEntry?.name == tag) {
                return true
            }
        }
        return false
    }




}
