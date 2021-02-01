package fr.isen.simon.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.simon.androiderestaurant.adapters.CategoryAdapter
import fr.isen.simon.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.simon.androiderestaurant.models.BasketService
import fr.isen.simon.androiderestaurant.models.Plat
import org.koin.android.ext.android.inject


class BasketActivity : AppCompatActivity() {

    private val basketService by inject<BasketService>()
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
