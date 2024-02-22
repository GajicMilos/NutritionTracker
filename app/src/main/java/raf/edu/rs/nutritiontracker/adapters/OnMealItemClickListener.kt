package raf.edu.rs.nutritiontracker.adapters

import raf.edu.rs.nutritiontracker.models.domain.Meal

interface OnMealItemClickListener {
    fun onItemClickListener(meal: Meal, isMeal: Boolean);
}