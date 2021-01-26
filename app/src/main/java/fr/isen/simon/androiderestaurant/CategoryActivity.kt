package fr.isen.simon.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding
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

        postShop(applicationContext)


        if(intent.extras != null){
            //Get extras:
            val title = intent.getStringExtra("title")
            binding.categoryTitle.text = title

            val staticListName = intent.getStringExtra("list")

            binding.listCategory.layoutManager = LinearLayoutManager(this)

            //Recuperer le string qui correspond
            val res = resources.getIdentifier(staticListName, "array", packageName)
            val strings = resources.getStringArray(res)
            val plats = ArrayList<Plat>()
            loop@ for (i in 1..strings.size){
                plats.add(Plat(strings[i - 1], "Description", 10.0f))
            }
            binding.listCategory.adapter = CategoryAdapter(plats){
                Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PlatDetailsActivity::class.java)
                intent.putExtra("title", it.name)
                intent.putExtra("description", it.description)
                intent.putExtra("price", it.tarif)

                startActivity(intent)
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")
    }

    fun postShop(context: Context?) {
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
        }) {
                error -> error.printStackTrace()
        }

        requestQueue.add(jsonObjectRequest)
    }
}
