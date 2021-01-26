package fr.isen.simon.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonElement
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding
import fr.isen.simon.androiderestaurant.models.DataJSON
import fr.isen.simon.androiderestaurant.models.Plat
import fr.isen.simon.androiderestaurant.models.ShopDataJSON
import org.json.JSONException
import org.json.JSONObject


private lateinit var binding: ActivityCategoryActivityBinding

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_activity)

        binding = ActivityCategoryActivityBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)


        if(intent.extras != null){
            //Get extras:
            val title = intent.getStringExtra("title")
            binding.categoryTitle.text = title

            val staticListName = intent.getStringExtra("list")

            binding.listCategory.layoutManager = LinearLayoutManager(this)

            loadShopCategory(title)
        }

    }

    private fun displayCategories(plats: ArrayList<Plat>) {
        binding.listCategory.adapter = CategoryAdapter(plats) {
            Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlatDetailsActivity::class.java)
            intent.putExtra("title", it.name)
            intent.putExtra("description", it.description)
            intent.putExtra("price", it.getFormattedPrice())

            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")
    }

    fun loadShopCategory(category : String) {
        val postUrl = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(this)
        val postData = JSONObject()

        try {
            postData.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST, postUrl, postData,{
            response -> println(response)
            //We parse the response in here

            val gson = Gson()
            val element: JsonElement = gson.fromJson(response.toString(), JsonElement::class.java)
            val json: DataJSON = gson.fromJson(element, DataJSON::class.java)
            println(json.data.size)
            json.data.forEach(action = {
                println(it.name)
                it.items.forEach(action = {
                    println(it.name)
                })
            })
            val plats = json.data.firstOrNull{ it.name == category }?.items
            if(plats != null){
                displayCategories(plats)
            }else{
                println("No category found !")
            }

            //displayCategories()
        }) {
            error -> error.printStackTrace()
        }

        requestQueue.add(jsonObjectRequest)
    }
}
