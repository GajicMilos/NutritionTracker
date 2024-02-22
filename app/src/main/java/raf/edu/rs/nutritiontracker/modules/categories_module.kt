package raf.edu.rs.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import raf.edu.rs.nutritiontracker.datasources.MealsDataSource
import raf.edu.rs.nutritiontracker.db.MealDatabase
import raf.edu.rs.nutritiontracker.presentation.viewmodel.ViewModel
import raf.edu.rs.nutritiontracker.repository.CategoryRepository
import raf.edu.rs.nutritiontracker.repository.CategoryRepositoryImpl
import raf.edu.rs.nutritiontracker.repository.DBMealRepository
import raf.edu.rs.nutritiontracker.repository.DBMealRepositoryImpl


val categoriesModule = module {

    viewModel { ViewModel(categoryRepository = get(), dbMealRepository = get()) }

    single<CategoryRepository> { CategoryRepositoryImpl(mealsDataSource = get()) }

    single<MealsDataSource> { create(get()) }

    single<DBMealRepository> { DBMealRepositoryImpl(get()) }

    single { get<MealDatabase>().getMealDao() }

}