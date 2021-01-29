package fr.isen.simon.androiderestaurant

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import fr.isen.simon.androiderestaurant.adapters.CarrouselAdapter
import fr.isen.simon.androiderestaurant.databinding.ActivityPlatDetailsBinding
import fr.isen.simon.androiderestaurant.models.Plat


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

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        //Get extras:
        val plat : Plat = intent.getSerializableExtra("plat") as Plat

        binding.button.setOnClickListener {
            //Toast.makeText(applicationContext, "Plat ajouté au panier", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "Plat ajouté au panier", Snackbar.LENGTH_SHORT).show()

            val cardAnim = binding.detailsAnimationCard
            lottieAnimationView.playAnimation()
            cardAnim.visibility = View.VISIBLE
        }

        binding.PlatSingleTitre.text = plat.name;
        binding.platSingleDescription.text = plat.description;
        binding.platIngredients.text = plat.ingredientsToString();

        binding.detailsPrice.text = plat.getFormattedPrice();
        displayCarrousel(plat.getAllPictures())
    }

    private fun displayCarrousel(images: List<String>?) {
        if(images != null) {
            val caroussel = binding.detailsCarrousel
            caroussel.adapter = images?.let { CarrouselAdapter(it) }
            val offset = 32.0f
            caroussel.setPageTransformer(ViewPager2.PageTransformer { page, position ->
                val myOffset = position * -2.0f * offset
                if (position < -1) {
                    page.translationX = -myOffset
                }else if (position <= 1.0f) {
                    //val scaleFactor = Math.max(0.7f, 1 - Math.abs(position/2.0f))
                    val scaleFactor = 1.0f
                    val alphaScale = 1 - Math.abs(position/2.0f)
                    page.translationX = Math.max(0.8f, alphaScale)
                    page.scaleY = Math.max(0.8f, alphaScale)
                    page.scaleX = page.scaleY
                    page.alpha =  alphaScale
                }
                else {
                        page.setAlpha(0.0f)
                        page.translationX = myOffset
                    }
                })
        }
    }
}