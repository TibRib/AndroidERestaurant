package fr.isen.simon.androiderestaurant.services

import android.content.Context

interface UserPreferencesService{
    fun setUserLoggedIn(value : Boolean)
    fun getUserLoggedIn() : Boolean
}

class UserPreferencesServiceImpl (
    private val context: Context
): UserPreferencesService {
    val PREFS_NAME = "sharedPreferences"
    val sharedPref =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun setUserLoggedIn(value: Boolean) {
        sharedPref ?: return
        with(sharedPref.edit()) {
            putBoolean("userLoggedIn", value)
            apply()
        }
    }

    override fun getUserLoggedIn(): Boolean {
        sharedPref ?: return false
        return sharedPref.getBoolean("userLoggedIn",false)
    }
}