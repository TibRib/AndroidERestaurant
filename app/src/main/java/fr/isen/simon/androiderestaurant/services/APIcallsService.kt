package fr.isen.simon.androiderestaurant.services

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonElement
import fr.isen.simon.androiderestaurant.models.*
import org.json.JSONObject

interface APIcallsService{
    fun queryCategory(categoryName : String) : LiveData<ArrayList<Plat>>
    fun registerUser(firstname : String, name: String,email: String,pass: String,address: String) : LiveData<UserDataJSON>
    fun loginUser(email: String, pass: String) : LiveData<UserDataJSON>
    fun makeOrder()
}

class APIcallsServiceImpl(
    private val context : Context
) : APIcallsService{
    override fun queryCategory(categoryName: String): LiveData<ArrayList<Plat>> {
        val categorydata = MutableLiveData<ArrayList<Plat>>()

        val postData = JSONObject()
            .put("id_shop", "1")

        callToAPI("http://test.api.catering.bluecodegames.com/menu", postData).observeForever {
            val shopCats = Gson().fromJson(it, ShopDataJSON::class.java)
            categorydata.postValue(shopCats.getPlatsOfCategory(categoryName))
        }
        return categorydata
    }

    override fun registerUser(
        firstname: String,name: String,email: String,pass: String,address: String
    ): LiveData<UserDataJSON>  {
        val userInfos = MutableLiveData<UserDataJSON>()

        val postData = JSONObject()
            .put("id_shop", "1")
            .put("firstname", firstname)
            .put("lastname", name)
            .put("email", email)
            .put("password", pass)
            .put("address", address)

        callToAPI("http://test.api.catering.bluecodegames.com/user/register", postData).observeForever {
            val parsedData: RegisterDataResponseJSON = Gson().fromJson(it, RegisterDataResponseJSON::class.java)
            if(parsedData.isSuccessful()){
                userInfos.postValue(parsedData.extractUser())
            }
        }
        return userInfos
    }

    override fun loginUser(email: String, pass: String): LiveData<UserDataJSON> {
        val userInfos = MutableLiveData<UserDataJSON>()

        val postData = JSONObject()
            .put("id_shop", "1")
            .put("email", email)
            .put("password", pass)

        callToAPI("http://test.api.catering.bluecodegames.com/user/login", postData).observeForever {
            val parsedData: RegisterDataResponseJSON = Gson().fromJson(it, RegisterDataResponseJSON::class.java)
            if(parsedData.isSuccessful()){
                userInfos.postValue(parsedData.extractUser())
            }
        }
        return userInfos
    }

    private fun callToAPI(postUrl : String, postData : JSONObject) : LiveData<JsonElement>{
        var result : MutableLiveData<JsonElement> = MutableLiveData<JsonElement>()
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST, postUrl, postData,{
                response -> println(response)
            val gson = Gson()
            val element: JsonElement = gson.fromJson(response.toString(), JsonElement::class.java)
            //Here I affect my result
            result.postValue(element)
        }) {
                error -> error.printStackTrace()
        }
        requestQueue.add(jsonObjectRequest)
        return result
    }

    override fun makeOrder() {
        TODO("Not yet implemented")
    }
}