package fr.isen.simon.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.simon.androiderestaurant.databinding.ActivityHomeBinding
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Starting Koin
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }

        Injector().printItemCount()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.entreesBtn.setOnClickListener{
            launchCategories("Entrées")
        }
        binding.platsBtn.setOnClickListener{
            launchCategories("Plats")
        }
        binding.dessertsBtn.setOnClickListener{
            launchCategories("Desserts")
        }

        binding.tempBasketButton.setOnClickListener {
            this.startActivity(Intent(applicationContext, BasketActivity::class.java))
        }

    }

    fun launchCategories(title : String){
        val intent = Intent(applicationContext, CategoryActivity::class.java)
        intent.putExtra("title", title)
        this.startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Home Activity Destroyed")
    }


}