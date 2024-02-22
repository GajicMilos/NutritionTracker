package raf.edu.rs.nutritiontracker.db;

import androidx.room.Database;
import androidx.room.TypeConverters;
import androidx.room.RoomDatabase
import raf.edu.rs.nutritiontracker.db.converters.StringMapConverter
import raf.edu.rs.nutritiontracker.db.dao.MealDao
import raf.edu.rs.nutritiontracker.db.entities.MealEntity
import rs.raf.vezbe10.data.local.db.converters.DateConverter
@Database(
    entities = [MealEntity::class,],
    version = 4,
    exportSchema = false)
@TypeConverters( DateConverter::class, StringMapConverter::class)
abstract class MealDatabase : RoomDatabase() {
    // Getteri za sve DAO-e moraju biti navedeni ovde
    abstract fun getMealDao(): MealDao
}