package fr.isen.simon.androiderestaurant.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.simon.androiderestaurant.R
import fr.isen.simon.androiderestaurant.ToolbarFragment
import fr.isen.simon.androiderestaurant.adapters.CategoryAdapter
import fr.isen.simon.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.simon.androiderestaurant.viewmodels.BasketViewModel
import fr.isen.simon.androiderestaurant.models.Plat
import org.koin.android.viewmodel.ext.android.viewModel


class BasketActivity : AppCompatActivity() {

    private val basketService by viewModel<BasketViewModel>()
    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root

        //Add the toolbar
        val toolbarFragment = ToolbarFragment.new("Panier : vos plats")
        supportFragmentManager.beginTransaction()
            .add(R.id.AppBarLayout, toolbarFragment)
            .commit()

        setContentView(view)

        binding.listBasket.layoutManager = LinearLayoutManager(this)

        loadPlats()

        binding.binButton.setOnClickListener {
            basketService.clearItems()
            loadPlats()
        }

        binding.orderBtn.setOnClickListener {
            this.startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

    }

    private fun loadPlats(){
        val price = basketService.getTotalPrice().toString() + "€"
        binding.totalBasket.text = "Total : ${price}"
        val nbArticles = basketService.getItemsCount().toString()
        binding.nbArticles.text = "Pour ${nbArticles} articles dans le panier"
        displayCategories(basketService.getItems())

    }

    private fun displayCategories(plats: ArrayList<Plat>) {
        binding.listBasket.adapter = CategoryAdapter(plats, {
            val intent = Intent(this, PlatDetailsActivity::class.java)
            intent.putExtra("plat",it)

            startActivity(intent)
        },{
            if(it.visibility == View.GONE) {
                it.visibility = View.VISIBLE
            }else{
                it.visibility = View.GONE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")

    }

}
