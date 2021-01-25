package fr.isen.simon.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater

private lateinit var binding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(LayoutInflater)
        setContentView(binding.root)

        //Next view
        binding.buttonEntrance.setOnClickListener{
            val intent = Intent(this, EntranceActivity::class.java)
        }
    }
}