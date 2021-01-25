package fr.isen.simon.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.simon.androiderestaurant.databinding.ActivityHomeBinding


private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.entreesBtn.setOnClickListener{
            val intent = Intent( applicationContext, CategoryActivity::class.java )
            intent.putExtra("title","Entrées")
            this.startActivity(intent)
        }
        binding.platsBtn.setOnClickListener{
            val intent = Intent( applicationContext, CategoryActivity::class.java )
            intent.putExtra("title","Plats")
            this.startActivity(intent)
        }
        binding.dessertsBtn.setOnClickListener{
            val intent = Intent( applicationContext, CategoryActivity::class.java )
            intent.putExtra("title","Desserts")
            this.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Home Activity Destroyed")
    }
}