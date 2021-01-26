package fr.isen.simon.androiderestaurant

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding
import fr.isen.simon.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.simon.androiderestaurant.databinding.ActivityPlatDetailsBinding

private lateinit var binding: ActivityPlatDetailsBinding

class PlatDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlatDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        //Get extras:
        val title = intent.getStringExtra("title")
        binding.PlatSingleTitre.text = title

        val desc = intent.getStringExtra("description")
        binding.platSingleDescription.text = desc

        val tarif = intent.getStringExtra("price")
        binding.detailsPrice.text = tarif

        if(intent.getStringExtra("image") != null){
            val picture = intent.getStringExtra("image")
            if(picture.isNotEmpty()) {
                Picasso.get()
                    .load(picture)
                    .placeholder(R.drawable.ic_menu_search)
                    .into(binding.platSinglePicture)
            }
        }
    }
}