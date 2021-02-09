package fr.isen.simon.androiderestaurant.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserPreferencesViewModel (
    private val context: Context
): ViewModel() {
    private val PREFS_NAME = "sharedPreferences"
    private val sharedPref =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val TAG = "User Preferences Service"

    private var _isLoggedIn = MutableLiveData<Boolean>()

    val isLoggedIn : LiveData<Boolean>
        get() =_isLoggedIn

    init {
        _isLoggedIn.postValue(loadSharedLoggedIn())
    }

    fun setLoggedIn(value : Boolean){
        setSharedLoggedIn(value)
        _isLoggedIn.postValue(value)
    }

    fun setSharedLoggedIn(value: Boolean) {
        sharedPref ?: return
        with(sharedPref.edit()) {
            putBoolean("userLoggedIn", value)
            apply()
        }
        Log.d(TAG,"userLoggedIn <- ${value.toString()}")
    }

    fun loadSharedLoggedIn(): Boolean {
        sharedPref ?: return false
        return sharedPref.getBoolean("userLoggedIn",false)
    }
}