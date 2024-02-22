package raf.edu.rs.nutritiontracker.models.domain

enum class Filter(val type:Char) {
    CATEGORY('c'),
    AREA('a'),
    INGREDIENT('i')
}