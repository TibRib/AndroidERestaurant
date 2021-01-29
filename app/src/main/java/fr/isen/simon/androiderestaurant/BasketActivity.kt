package fr.isen.simon.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.simon.androiderestaurant.adapters.CategoryAdapter
import fr.isen.simon.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.simon.androiderestaurant.models.Plat


private lateinit var binding: ActivityBasketBinding

class BasketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_activity)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.listBasket.layoutManager = LinearLayoutManager(this)

        loadPlats()

        binding.binButton.setOnClickListener {
            Injector().basketService.clearItems()
            loadPlats()
        }

    }

    private fun loadPlats(){
        val price = Injector().basketService.getTotalPrice().toString() + "€"
        binding.totalBasket.text = "Total : ${price}"
        val nbArticles = Injector().basketService.getItemsCount().toString()
        binding.nbArticles.text = "Pour ${nbArticles} articles dans le panier"
        displayCategories(Injector().basketService.getItems())

    }

    private fun displayCategories(plats: ArrayList<Plat>) {
        binding.listBasket.adapter = CategoryAdapter(plats) {
            val intent = Intent(this, PlatDetailsActivity::class.java)
            intent.putExtra("plat", it)

            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")

    }

}
