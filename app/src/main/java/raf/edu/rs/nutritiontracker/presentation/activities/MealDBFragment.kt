package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import orgraf.projekatrma.fragments.DeleteConfirmFragment
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.db.converters.MealEntityMealConverter
import raf.edu.rs.nutritiontracker.models.domain.Meal
import raf.edu.rs.nutritiontracker.presentation.contracts.DBContract
import raf.edu.rs.nutritiontracker.presentation.contracts.PlanContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel

class MealDBFragment(private val mealDb: Meal) : Fragment(R.layout.meal_db_fragment) {

  //  private val postsViewModel: MainContract.CategoriesViewModel by viewModel<CategoryViewModel>()
    private val mealDbViewModel: DBContract.ViewModel by viewModel<ViewModel>()
    private val planContract: PlanContract.ViewModel by viewModel<ViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealDbViewModel.getMealById(mealDb.idMeal.toInt())
        var update=view.findViewById<Button>(R.id.updateMealDbButton)
        var delete=view.findViewById<Button>(R.id.deleteMealDbButton)
        var tvMealName=view.findViewById<TextView>(R.id.tvMealNameDB)
        var tvMealCategory=view.findViewById<TextView>(R.id.tvMealCategoryDB)
        var tvMealArea = view.findViewById<TextView>(R.id.tvMealAreaDB)
        var tvInstructions  =view.findViewById<TextView>(R.id.tvInstructionsDB)
        var tvIngredientsVal=view.findViewById<TextView>(R.id.tvIngredientsValDB)
        var tvLink=view.findViewById<TextView>(R.id.tvLinkDB)
        var tvTags =view.findViewById<TextView>(R.id.tvTagsDB)
        var imageView=view.findViewById<ImageView>(R.id.mealDBImageView)
        var scvMealDb=view.findViewById<ScrollView>(R.id.scvMealDb)
        var progressBar:ProgressBar=view.findViewById(R.id.progressBarMealDb)
        progressBar.isVisible=true
        scvMealDb.isVisible=false

        mealDbViewModel.singleMealDb.observe(viewLifecycleOwner){
            val meal=it;
            delete.setOnClickListener{
                Toast.makeText(view.context,"r u sure u want to delete",Toast.LENGTH_SHORT).show()
                    val dialog = DeleteConfirmFragment(MealEntityMealConverter.fromMeal(mealDb)!!)
                    dialog.show(activity?.supportFragmentManager!!, "confirmation_dialog_tag")


            }
            update.setOnClickListener{
                var fragmentTransaction=parentFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addFragmentFcv,UpdateMealDBFragment(meal!!))
                fragmentTransaction.addToBackStack("back3")
                fragmentTransaction.commit()
                Toast.makeText(view.context,"update",Toast.LENGTH_SHORT).show()

            }
            val fragmentTag = "PlanByDayFragment"
            val fragment = parentFragmentManager.findFragmentByTag(fragmentTag)
            val isFragmentOnBackStack = fragment != null && fragment.isInBackStack()

            if(isFragmentOnBackStack){
                update.text="add to plan"
                update.setOnClickListener {
                    planContract.setSelectedMeal(MealEntityMealConverter.toMeal(meal))

                    println("selectedMeal value in first fragment: ${planContract.selectedMeal.value}")

                    val fragment = parentFragmentManager.findFragmentByTag("PlanByDayFragment")
                    if(fragment is PlanByDayFragment){
                        fragment.selectedMeal=MealEntityMealConverter.toMeal(meal);
                    }
                    parentFragmentManager.popBackStack("PlanByDayFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)


                }
            }


            tvMealName.text=if (meal?.strMeal==null) "Not Available" else meal.strMeal
            tvMealArea.text=if (meal?.strArea==null) "Not Available" else meal.strArea
            tvMealCategory.text=if (meal?.strCategory==null) "Not Available" else meal.strCategory
            tvInstructions.text=if (meal?.strInstructions==null) "Not Available" else meal.strInstructions
            tvLink.text=if (meal?.strYoutube==null) "Not Available" else meal.strYoutube
            tvTags.text=if (meal?.strTags==null) "Not Available" else meal.strTags
            Glide.with(view.context).load( if (meal?.strImageSource == null) meal?.strMealThumb else meal.strImageSource).into(imageView);
            var sb:StringBuilder=StringBuilder();
            for(i in 1..20){
                if(meal?.strIngredients?.get("strIngredient$i").isNullOrBlank())
                    break;
                sb.append( meal?.strIngredients?.get("strIngredient$i"))
                sb.append(": ")
                sb.append( meal?.strMeasure?.get("strMeasure$i"))
                sb.append("\n")
            }
            tvIngredientsVal.text=sb.toString()


            progressBar.isVisible=false
            scvMealDb.isVisible=true
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