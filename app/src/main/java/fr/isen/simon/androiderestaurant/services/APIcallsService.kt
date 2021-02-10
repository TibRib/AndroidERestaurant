package fr.isen.simon.androiderestaurant.services

import android.content.Context
import android.util.Log
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
    fun registerUser(firstname : String, name: String,email: String,pass: String,address: String) : LiveData<UserDataJSON?>
    fun loginUser(email: String, pass: String) : LiveData<UserDataJSON?>
    fun makeOrder(idUser : String, basket : BasketData) : LiveData<List<OrderDataJSON>>
    fun listOrders(idUser : String) : LiveData<List<OrderDataJSON>>

}

class APIcallsServiceImpl(
    private val context : Context
) : APIcallsService{
    private val TAG = "API Calls Service"
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
    ): LiveData<UserDataJSON?>  {
        val userInfos = MutableLiveData<UserDataJSON?>()

        val postData = JSONObject()
            .put("id_shop", "1")
            .put("firstname", firstname)
            .put("lastname", name)
            .put("email", email)
            .put("password", pass)
            .put("address", address)

        callToAPI("http://test.api.catering.bluecodegames.com/user/register", postData).observeForever {
            var parsedData = RegisterDataResponseJSON(null,null)
            try {
                parsedData = Gson().fromJson(it, RegisterDataResponseJSON::class.java)
            }catch (t : Throwable){}
            if(parsedData.isSuccessful()){
                userInfos.postValue(parsedData.extractUser())
            }else{
                userInfos.postValue(null)
            }
        }
        return userInfos
    }

    override fun loginUser(email: String, pass: String): LiveData<UserDataJSON?> {
        val userInfos = MutableLiveData<UserDataJSON?>()

        val postData = JSONObject()
            .put("id_shop", "1")
            .put("email", email)
            .put("password", pass)

        callToAPI("http://test.api.catering.bluecodegames.com/user/login", postData).observeForever {
            Log.d(TAG, it.toString())
            var parsedData = RegisterDataResponseJSON(null,null)
            try {
                parsedData = Gson().fromJson(it, RegisterDataResponseJSON::class.java)
            }catch (t : Throwable){}
            if(parsedData.isSuccessful()){
                userInfos.postValue(parsedData.extractUser())
            }else{
                userInfos.postValue(null)
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

    override fun makeOrder(idUser : String, basket : BasketData) : LiveData<List<OrderDataJSON>> {
        val orders = MutableLiveData<List<OrderDataJSON>>()

        val postData = JSONObject()
            .put("id_shop", "1")
            .put("id_user", idUser)
            .put("msg", Gson().toJson(basket))

        callToAPI("http://test.api.catering.bluecodegames.com/user/order", postData).observeForever {
            val parsedData: OrderResponseDataJSON = Gson().fromJson(it, OrderResponseDataJSON::class.java)
            if(parsedData.isSuccessful()){
                orders.postValue(parsedData.extractOrders())
            }
        }
        return orders
    }

    override fun listOrders(idUser : String) : LiveData<List<OrderDataJSON>> {
        val orders = MutableLiveData<List<OrderDataJSON>>()

        val postData = JSONObject()
            .put("id_shop", "1")
            .put("id_user", idUser)

        callToAPI("http://test.api.catering.bluecodegames.com/user/listorders", postData).observeForever {
            val parsedData: OrderResponseDataJSON = Gson().fromJson(it, OrderResponseDataJSON::class.java)
            if(parsedData.isSuccessful()){
                orders.postValue(parsedData.extractOrders())
            }
        }
        return orders
    }
}