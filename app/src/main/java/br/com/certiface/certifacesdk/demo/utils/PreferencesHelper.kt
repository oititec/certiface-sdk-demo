package br.com.certiface.certifacesdk.demo.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferencesHelper {
    private const val PREFS_NAME = "certiface_sdk_prefs"
    private const val KEY_RESULTS_HISTORY = "results_history"
    private const val MAX_HISTORY_SIZE = 100

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveResultsHistory(context: Context, results: List<String>) {
        val gson = Gson()
        val json = gson.toJson(results.takeLast(MAX_HISTORY_SIZE))
        getSharedPreferences(context).edit().putString(KEY_RESULTS_HISTORY, json).apply()
    }

    fun getResultsHistory(context: Context): List<String> {
        val json = getSharedPreferences(context).getString(KEY_RESULTS_HISTORY, null)
        return if (json != null) {
            try {
                val gson = Gson()
                val type = object : TypeToken<List<String>>() {}.type
                gson.fromJson<List<String>>(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun clearResultsHistory(context: Context) {
        getSharedPreferences(context).edit().remove(KEY_RESULTS_HISTORY).apply()
    }
}

