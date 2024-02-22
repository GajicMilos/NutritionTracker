package raf.edu.rs.nutritiontracker.presentation.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import raf.edu.rs.nutritiontracker.R
import timber.log.Timber

class MainActivity: AppCompatActivity(R.layout.activity_main) {
  lateinit var   bottomNavigationView:BottomNavigationView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        Timber.e(bottomNavigationView.toString())
        val x = 0
        var ff: FilterFragment? = null
        var mf: MainFragment? = null
        var gf: GraphFragment? = null



        bottomNavigationView.setOnItemSelectedListener { v: MenuItem ->
            when (v.itemId) {
                R.id.page_1 -> {

                    val transaction = supportFragmentManager.beginTransaction()

                     if (mf == null) {
                        mf = MainFragment()
                    }

                    transaction.replace(R.id.addFragmentFcv, mf!!)
                    transaction.commit()
                }

                R.id.page_2 -> {

                    val transaction = supportFragmentManager.beginTransaction()

                    if (ff == null) {
                        ff = FilterFragment()
                    }

                    transaction.replace(R.id.addFragmentFcv, ff!!)
                    transaction.commit()
                }
                R.id.page_3->{


                    val transaction = supportFragmentManager.beginTransaction()
                    if (gf == null) {
                        gf = GraphFragment()
                    }
                    transaction.replace(R.id.addFragmentFcv, gf!!)
                    transaction.commit()
                }


            }
            true
        }



        bottomNavigationView.selectedItemId=R.id.page_1
    }

}