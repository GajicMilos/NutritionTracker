package raf.edu.rs.nutritiontracker.adapters

import raf.edu.rs.nutritiontracker.models.domain.Filter

interface OnFilterItemClickListener {
    fun onItemClick(type: Filter, category: String)
}