package raf.edu.rs.nutritiontracker.presentation.activities

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import org.koin.androidx.viewmodel.ext.android.viewModel
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.presentation.contracts.DBContract
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel
import java.lang.IndexOutOfBoundsException
import java.util.Calendar
import java.util.Date


class GraphFragment : Fragment(R.layout.graph_fragment) {
    private val mealDbViewModel: DBContract.ViewModel by viewModel<ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealDbViewModel.get7dayCount();

        Handler(Looper.getMainLooper()).postDelayed({

            var chart = view.findViewById<PieChart>(R.id.pieChart)

            // Example data: dates and corresponding numbers
            var dates = listOf<String>()
            val numbers = listOf(5f, 10f, 8f, 12f, 6f)

            val entries = mutableListOf<PieEntry>()
            val list=mealDbViewModel.days7cound.value!!;
            var minusDay=0;
            for (i in 6 downTo 0) {

                try {

                    entries.add(PieEntry( list[i].mealCount.toFloat(),list[i].day))


                }catch (e:IndexOutOfBoundsException){
             //       minusDay+=1;
               //     entries.add(PieEntry(i.toFloat(), 0f))
                 //   dates=dates.plus(formatDate(list[0].day,minusDay))
                }

            }
            println( mealDbViewModel.days7cound.value!!)

            val dataSet = PieDataSet(entries, "Dani")
            dataSet.valueTextSize = 16f
            dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.WHITE)
            val data = PieData(dataSet)
            chart.apply {
                setUsePercentValues(false)
                description.isEnabled = false
                legend.isEnabled = true
                holeRadius = 1f
                transparentCircleRadius = 1f
                setEntryLabelTextSize(16f)
                setEntryLabelColor(Color.BLACK)
                animateY(1000)


            }
            chart.data = data

            chart.invalidate()

        },150)
             var makePlan: Button =view.findViewById(R.id.makeAPlanButton)
            makePlan.setOnClickListener {
                var fragmentTransaction=parentFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addFragmentFcv,DayFormFragment())
                fragmentTransaction.addToBackStack("dayForm")
                fragmentTransaction.commit()

            }


        }


        private fun formatDate(date: Date, minusDay: Int):String{
            val calendar = Calendar.getInstance()
            calendar.time = date


            return " "+ (calendar.get(Calendar.DAY_OF_MONTH)-minusDay) + " " + (calendar.get(Calendar.MONTH)+1) + " " + calendar.get(Calendar.YEAR)

        }
    }




