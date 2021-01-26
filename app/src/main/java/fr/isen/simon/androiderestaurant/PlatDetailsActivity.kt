package fr.isen.simon.androiderestaurant

import android.R
import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import fr.isen.simon.androiderestaurant.databinding.ActivityPlatDetailsBinding


private lateinit var binding: ActivityPlatDetailsBinding

class PlatDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlatDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val lottieAnimationView = binding.animationView
        lottieAnimationView.imageAssetsFolder = "images/"
        binding.cookanimation.imageAssetsFolder = "images/"

        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                val cardAnim = binding.detailsAnimationCard
                cardAnim.visibility = View.GONE
                println("Finished !")
            }

            override fun onAnimationCancel(animation: Animator) { }

            override fun onAnimationRepeat(animation: Animator) {}
        })

        //Get extras:
        val title = intent.getStringExtra("title")
        binding.PlatSingleTitre.text = title

        binding.button.setOnClickListener {
            Toast.makeText(applicationContext, "Plat ajouté au panier", Toast.LENGTH_SHORT).show()
            val mySnackbar = Snackbar.make(view, "Plat ajouté au panier", Snackbar.LENGTH_SHORT)

            val cardAnim = binding.detailsAnimationCard
            cardAnim.visibility = View.VISIBLE

        }

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