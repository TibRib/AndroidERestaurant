package fr.isen.simon.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.simon.androiderestaurant.databinding.ActivityHomeBinding

private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.entreesBtn.setOnClickListener{
            Toast.makeText(applicationContext, "Clicked Entrees", Toast.LENGTH_SHORT).show()
        }
        binding.platsBtn.setOnClickListener{
            Toast.makeText(applicationContext, "Clicked Plats", Toast.LENGTH_SHORT).show()
        }
        binding.dessertsBtn.setOnClickListener{
            Toast.makeText(applicationContext, "Clicked Desserts", Toast.LENGTH_SHORT).show()
        }
    }

}