package fr.isen.simon.androiderestaurant

import android.content.Intent.getIntent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding


private lateinit var binding: ActivityCategoryActivityBinding

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_activity)

        binding = ActivityCategoryActivityBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        //Get extras:
        val title = intent.getStringExtra("title")
        binding.categoryTitle.text = title

    }
    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")
    }
}