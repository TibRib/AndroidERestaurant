package fr.isen.simon.androiderestaurant.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.simon.androiderestaurant.R
import fr.isen.simon.androiderestaurant.ToolbarFragment
import fr.isen.simon.androiderestaurant.adapters.CategoryAdapter
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding
import fr.isen.simon.androiderestaurant.models.Plat
import fr.isen.simon.androiderestaurant.services.APIcallsService
import fr.isen.simon.androiderestaurant.viewmodels.BasketViewModel
import fr.isen.simon.androiderestaurant.viewmodels.UserPreferencesViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryActivityBinding
    private val apiService by inject<APIcallsService>()
    private val basketVM by viewModel<BasketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryActivityBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        if(intent.extras != null){
            //Get extras:
            val title = intent.getStringExtra("title")
            binding.categoryTitle.text = title
            //Add the toolbar
            val toolbarFragment = ToolbarFragment.new("Les ${title.toLowerCase()}")
            supportFragmentManager.beginTransaction()
                .add(R.id.AppBarLayout, toolbarFragment)
                .commit()
            val staticListName = intent.getStringExtra("list")
            binding.listCategory.layoutManager = LinearLayoutManager(this)
            loadShopCategory(title!!)
        }
    }

    private fun displayCategories(plats: ArrayList<Plat>) {
        binding.listCategory.adapter = CategoryAdapter(plats, {
            val intent = Intent(this, PlatDetailsActivity::class.java)
            intent.putExtra("plat",it)

            startActivity(intent)
        },{})

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")

    }

    fun loadShopCategory(category : String) {
        //New version : loads the information from the API service
        apiService.queryCategory(category).observeForever {
            if(it != null) {
                println("Réponse recue !! : ${it.size} plats")
                it.forEach(action = {
                    println(it.name)
                })

                displayCategories(it)
            }else {
                println("No category found !")
            }
        }
    }
}
