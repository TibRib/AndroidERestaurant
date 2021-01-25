package fr.isen.simon.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding


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

            //Recuperer le string qui correspond
            val res = resources.getIdentifier(staticListName, "array", packageName)
            val strings = resources.getStringArray(res)
            val plats = ArrayList<Plat>()
            loop@ for (i in 1..strings.size){
                plats.add(Plat(strings[i - 1], "", 0.0f))
            }
            binding.listCategory.adapter = CategoryAdapter(plats)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")
    }
}