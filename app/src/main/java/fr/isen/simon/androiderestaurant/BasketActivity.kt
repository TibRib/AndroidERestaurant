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
import fr.isen.simon.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding
import fr.isen.simon.androiderestaurant.models.DataJSON
import fr.isen.simon.androiderestaurant.models.Plat
import org.json.JSONException
import org.json.JSONObject


private lateinit var binding: ActivityBasketBinding

class BasketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_activity)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.listBasket.layoutManager = LinearLayoutManager(this)

        val plats : ArrayList<Plat> = Injector().basketService.getItems()
        displayCategories(plats)

    }

    private fun displayCategories(plats: ArrayList<Plat>) {
        binding.listBasket.adapter = CategoryAdapter(plats) {
            val intent = Intent(this, PlatDetailsActivity::class.java)
            intent.putExtra("plat",it)

            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")

    }

}
