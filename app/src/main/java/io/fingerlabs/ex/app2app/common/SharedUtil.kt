package io.fingerlabs.ex.app2app.common

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedUtil(private val context: Context) {

    companion object {
        private const val NAME = "app2appSample"
    }

    fun saveIntValue(key: String, value: Int) {
        context.getSharedPreferences(NAME, MODE_PRIVATE).apply {
            edit().putInt(key, value).apply()
        }
    }

    fun loadIntValue(key: String, defaultValue: Int = -1): Int {
        return context.getSharedPreferences(NAME, MODE_PRIVATE).run {
            getInt(key, defaultValue)
        }
    }


    fun saveStringValue(key: String, value: String) {
        context.getSharedPreferences(NAME, MODE_PRIVATE).apply {
            edit().putString(key, value).apply()
        }
    }

    fun loadStringValue(key: String, defaultValue: String? = null): String? {
        return context.getSharedPreferences(NAME, MODE_PRIVATE).run {
            getString(key, defaultValue)
        }
    }
}