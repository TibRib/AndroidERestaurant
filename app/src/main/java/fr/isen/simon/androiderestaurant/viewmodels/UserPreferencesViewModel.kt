package fr.isen.simon.androiderestaurant.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import fr.isen.simon.androiderestaurant.models.UserDataJSON

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

    fun writeUser(user : UserDataJSON){
        sharedPref ?: return
        val usrJson : String = Gson().toJson(user)
        with(sharedPref.edit()) {
            putString("currentUser", usrJson)
            apply()
        }
        Log.d(TAG,"currentUser <- ${usrJson}")
    }

    fun readUser() : UserDataJSON ?{
        sharedPref ?: return null
        val usrJson =  sharedPref.getString("currentUser","")
        if (usrJson != null) {
            if(usrJson.isNotEmpty()){
                return Gson().fromJson(usrJson, UserDataJSON::class.java)
            }
        }
        return null
    }
}