package fr.isen.simon.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonElement
import fr.isen.simon.androiderestaurant.adapters.CategoryAdapter
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding
import fr.isen.simon.androiderestaurant.models.CategoryDataJSON
import fr.isen.simon.androiderestaurant.models.Plat
import fr.isen.simon.androiderestaurant.services.APIcallsService
import fr.isen.simon.androiderestaurant.services.UserPreferencesService
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryActivityBinding
    private val apiService by inject<APIcallsService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryActivityBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        if(intent.extras != null){
            //Get extras:
            val title = intent.getStringExtra("title")
            binding.categoryTitle.text = title
            //Add the toolbar
            val toolbarFragment = ToolbarFragment.new("Les ${title.toLowerCase()}")
            supportFragmentManager.beginTransaction()
                .add(R.id.AppBarLayout, toolbarFragment)
                .commit()
            val staticListName = intent.getStringExtra("list")
            binding.listCategory.layoutManager = LinearLayoutManager(this)
            loadShopCategory(title!!)
        }
    }

    private fun displayCategories(plats: ArrayList<Plat>) {
        binding.listCategory.adapter = CategoryAdapter(plats, {
            val intent = Intent(this, PlatDetailsActivity::class.java)
            intent.putExtra("plat",it)

            startActivity(intent)
        },{})

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")

    }

    fun loadShopCategory(category : String) {
        /*
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
            val jsonCategory: CategoryDataJSON = gson.fromJson(element, CategoryDataJSON::class.java)
            println(jsonCategory.data.size)
            jsonCategory.data.forEach(action = {
                println(it.name)
                it.items.forEach(action = {
                    println(it.name)
                })
            })
            val plats = jsonCategory.data.firstOrNull{ it.name == category }?.items
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

         */
        apiService.queryCategory(category).observeForever {
            println("Réponse recue !! : ${it.data.size} plats")
            it.data.forEach(action = {
                println(it.name)
                it.items.forEach(action = {
                    println(it.name)
                })
            })
            val plats = it.data.firstOrNull{ it.name == category }?.items
            if(plats != null){
                displayCategories(plats)
            }else{
                println("No category found !")
            }
        }
    }
}
