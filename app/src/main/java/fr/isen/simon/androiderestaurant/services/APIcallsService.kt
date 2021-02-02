package fr.isen.simon.androiderestaurant.services

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import fr.isen.simon.androiderestaurant.models.CategoryDataJSON
import fr.isen.simon.androiderestaurant.models.RegisterDataResponseJSON
import fr.isen.simon.androiderestaurant.models.UserDataJSON
import org.json.JSONException
import org.json.JSONObject

interface APIcallsService{
    fun queryCategory(categoryName : String) : LiveData<CategoryDataJSON>
    fun registerUser(firstname : String, name: String,email: String,pass: String,address: String) : LiveData<UserDataJSON>
    fun loginUser(email: String, pass: String) : LiveData<UserDataJSON>
    fun makeOrder()
}

class APIcallsServiceImpl(
    private val context : Context
) : APIcallsService{
    override fun queryCategory(categoryName: String): LiveData<CategoryDataJSON> {
        val categorydata = MutableLiveData<CategoryDataJSON>()
        val postUrl = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(context)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST, postUrl, postData,{
            response -> println(response)

            val gson = Gson()
            val element: JsonElement = gson.fromJson(response.toString(), JsonElement::class.java)
            val jsonCategory: CategoryDataJSON = gson.fromJson(element, CategoryDataJSON::class.java)
            if(jsonCategory != null){
                categorydata.postValue(jsonCategory)
            }
        }) {
            error -> error.printStackTrace()
        }
        requestQueue.add(jsonObjectRequest)
        return categorydata
    }

    override fun registerUser(
        firstname: String,
        name: String,
        email: String,
        pass: String,
        address: String
    ): LiveData<UserDataJSON>  {
        val userInfos = MutableLiveData<UserDataJSON>()

        val postUrl = "http://test.api.catering.bluecodegames.com/user/register"
        val requestQueue = Volley.newRequestQueue(context)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("firstname", firstname)
            postData.put("lastname", name)
            postData.put("email", email)
            postData.put("password", pass)
            postData.put("address", address)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST, postUrl, postData,{
                response -> println(response)
            //Parse the response :
            val gson = Gson()
            val element: JsonElement = gson.fromJson(response.toString(), JsonElement::class.java)
            val json: RegisterDataResponseJSON = gson.fromJson(element, RegisterDataResponseJSON::class.java)

            if(json.isSuccessful()){
                userInfos.postValue(json.extractUser())
            }
        }) {
                error -> error.printStackTrace()
        }
        requestQueue.add(jsonObjectRequest)
        return userInfos
    }

    override fun loginUser(email: String, pass: String): LiveData<UserDataJSON> {
        val userInfos = MutableLiveData<UserDataJSON>()

        val postUrl = "http://test.api.catering.bluecodegames.com/user/login"
        val requestQueue = Volley.newRequestQueue(context)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("email", email)
            postData.put("password", pass)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST, postUrl, postData,{
                response -> println(response)
            //Parse the response :
            val gson = Gson()
            val element: JsonElement = gson.fromJson(response.toString(), JsonElement::class.java)
            val json: RegisterDataResponseJSON = gson.fromJson(element, RegisterDataResponseJSON::class.java)

            if(json.isSuccessful()){
                userInfos.postValue(json.extractUser())
            }
        }) {
                error -> error.printStackTrace()
        }
        requestQueue.add(jsonObjectRequest)
        return userInfos
    }

    override fun makeOrder() {
        TODO("Not yet implemented")
    }
}