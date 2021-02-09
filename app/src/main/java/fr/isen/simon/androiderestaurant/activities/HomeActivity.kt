package fr.isen.simon.androiderestaurant.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.simon.androiderestaurant.R
import fr.isen.simon.androiderestaurant.ToolbarFragment
import fr.isen.simon.androiderestaurant.appModule
import fr.isen.simon.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.simon.androiderestaurant.viewmodels.BasketViewModel
import fr.isen.simon.androiderestaurant.viewmodels.UserPreferencesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val userVM by viewModel<UserPreferencesViewModel>()
    private val basketVM by viewModel<BasketViewModel>()

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
            val isLoggedInValue : Boolean? = userVM.isLoggedIn.value
            if(isLoggedInValue != null) {
                val nbool = !(isLoggedInValue)
                userVM.setLoggedIn(nbool)
                Toast.makeText(applicationContext, "User Logged In = ${nbool}", Toast.LENGTH_SHORT)
                    .show()
            }
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