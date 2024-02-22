package raf.edu.rs.nutritiontracker.db.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.lang.reflect.Type

class StringMapConverter: KoinComponent {

    private val jsonAdapter: JsonAdapter<Map<String,String>>

    init {
        val type: Type =  Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val moshi: Moshi = get()

        jsonAdapter = moshi.adapter(type)

    }

    @TypeConverter
    fun fromString(value: String?): Map<String, String>? {
        if (value == null) {
            return null
        }

        return try {
            jsonAdapter.fromJson(value)
        } catch (e: Exception) {
            throw IllegalArgumentException("Error converting string to map", e)
        }
    }

    @TypeConverter
    fun toString(value: Map<String, String>?): String? {
        if (value == null) {
            return null
        }

        return try {
            jsonAdapter.toJson(value)
        } catch (e: Exception) {
            throw IllegalArgumentException("Error converting map to string", e)
        }
    }
}