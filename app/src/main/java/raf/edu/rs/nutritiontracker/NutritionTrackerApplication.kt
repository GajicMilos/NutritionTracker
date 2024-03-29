package raf.edu.rs.nutritiontracker

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import raf.edu.rs.nutritiontracker.modules.coreModule
import raf.edu.rs.nutritiontracker.modules.categoriesModule
import timber.log.Timber

class NutritionTrackerApplication: Application() {

    companion object {
        lateinit var  user:User;
    }
        override fun onCreate() {

            super.onCreate()
            init()

        }

        private fun init() {
            initTimber()
            initKoin()
        }

        private fun initTimber() {
            Timber.plant(Timber.DebugTree())
        }

        private fun initKoin() {
            val modules = listOf(
                coreModule,
                categoriesModule,


            )
            startKoin {
                androidLogger(Level.ERROR)
                // Use application context
                androidContext(this@NutritionTrackerApplication)
                // Use properties from assets/koin.properties
                androidFileProperties()
                // Use koin fragment factory for fragment instantiation
                fragmentFactory()
                // modules
                modules(modules)
            }
        }

    }
