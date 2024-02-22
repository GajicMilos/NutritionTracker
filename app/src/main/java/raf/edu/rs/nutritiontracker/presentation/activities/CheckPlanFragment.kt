package raf.edu.rs.nutritiontracker.presentation.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.presentation.contracts.PlanContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel
import java.lang.StringBuilder


class CheckPlanFragment :Fragment(R.layout.check_plan_fragment){
    private val planContract: PlanContract.ViewModel by viewModel<ViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvMonday:TextView=view.findViewById(R.id.planMondayTv)

        val sendToEmail: Button =view.findViewById(R.id.sendPlanButton)
        val etEmail:EditText=view.findViewById(R.id.emailToSendToEt)
        var map=    planContract.getWeekPlan().value;

        //sb2 ako treba razlicite
       val sb:StringBuilder= StringBuilder()
        val sb2=StringBuilder()
        for (key in map?.keys!!) {
            val value = map[key]
           sb.append(key)
            sb2.append(key)
            sb.append(": \n")
            sb2.append(": \n")
            for(key2 in value?.keys!!){
                val xd = value[key2]
                sb.append(key2)
                sb2.append(key2)
                sb.append(": ")
                sb2.append(": ")
                sb.append(xd!!.strMeal)
                sb2.append(xd.toString())
                sb.append("\n")
                sb2.append("\n")
            }
        }
        tvMonday.text=sb;




        sendToEmail.setOnClickListener {
           val email= etEmail.text.toString()
            val subject = "Nutrition Tracker Plan"
            val message = sb2.toString();

            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            i.putExtra(Intent.EXTRA_SUBJECT, subject)
            i.putExtra(Intent.EXTRA_TEXT, message)
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    "There are no email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


    }




}