package orgraf.projekatrma.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.db.entities.MealEntity
import raf.edu.rs.nutritiontracker.presentation.contracts.DBContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel


class DeleteConfirmFragment(var meal:MealEntity) : DialogFragment() {
    private val mealDbViewModel: DBContract.ViewModel by viewModel<ViewModel>()



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Build the dialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete meal")
            .setMessage("R u sure u want to delete")
            .setPositiveButton("Confirm"
            ) { _, _ ->
                mealDbViewModel.deleteMeal(meal, object : DBContract.InsertMealCallback {
                    override fun onMealInserted() {
                        Toast.makeText(context, R.string.successDeleting, Toast.LENGTH_SHORT).show()
                    }

                    override fun onInsertError(error: Throwable) {
                        Toast.makeText(context, "DELETE NOT SUCCESFULL", Toast.LENGTH_SHORT).show()
                    }
                })
                activity?.supportFragmentManager?.popBackStack();

                //finish fragment
            }
            .setNegativeButton("cancel"
            ) { _, _ ->
                // User canceled the action

            }

        // Return the built dialog
        return builder.create()
    }
}