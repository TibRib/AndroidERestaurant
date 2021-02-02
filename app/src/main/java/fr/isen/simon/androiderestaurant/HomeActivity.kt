package fr.isen.simon.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import fr.isen.simon.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.simon.androiderestaurant.services.UserPreferencesService
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val userPreferences by inject<UserPreferencesService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Starting Koin
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        //Add the toolbar
        val toolbarFragment = ToolbarFragment.new("Accueil : Explorez nos menus")
        supportFragmentManager.beginTransaction()
            .add(R.id.AppBarLayout, toolbarFragment)
            .commit()

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

        binding.loginButton.setOnClickListener {
            userPreferences.setUserLoggedIn(!userPreferences.getUserLoggedIn())
            Toast.makeText(applicationContext, "User Logged In = ${userPreferences.getUserLoggedIn()}", Toast.LENGTH_SHORT).show()
        }

    }

    fun launchCategories(title: String){
        val intent = Intent(applicationContext, CategoryActivity::class.java)
        intent.putExtra("title", title)
        this.startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Home Activity Destroyed")
    }


}