package jw.adamiak.anothermoviesearchapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieTypeConverters {
	@TypeConverter
	fun fromJsonToListInt(json: String?): List<Int>? {
		val gson = Gson()
		val type = object : TypeToken<List<Int?>?>() {}.type
		return gson.fromJson<List<Int>>(json, type)
	}

	@TypeConverter
	fun fromListIntToJson(list: List<Int?>?): String? {
		val gson = Gson()
		val type = object : TypeToken<List<Int?>?>() {}.type
		return gson.toJson(list, type)
	}

	@TypeConverter
	fun fromJsonToListString(json: String?): List<String>? {
		val gson = Gson()
		val type = object : TypeToken<List<String>?>() {}.type
		return gson.fromJson<List<String>>(json, type)
	}

	@TypeConverter
	fun fromListStringToJson(list: List<String>?): String? {
		val gson = Gson()
		val type = object : TypeToken<List<String>?>() {}.type
		return gson.toJson(list, type)
	}

}