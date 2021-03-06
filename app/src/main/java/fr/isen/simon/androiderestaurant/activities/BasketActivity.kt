package fr.isen.simon.androiderestaurant.activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.simon.androiderestaurant.R
import fr.isen.simon.androiderestaurant.ToolbarFragment
import fr.isen.simon.androiderestaurant.adapters.CategoryAdapter
import fr.isen.simon.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.simon.androiderestaurant.models.BasketData
import fr.isen.simon.androiderestaurant.viewmodels.BasketViewModel
import fr.isen.simon.androiderestaurant.models.Plat
import fr.isen.simon.androiderestaurant.services.APIcallsService
import fr.isen.simon.androiderestaurant.viewmodels.UserPreferencesViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class BasketActivity : AppCompatActivity() {

    private val basketService by viewModel<BasketViewModel>()
    private val userPreferences by viewModel<UserPreferencesViewModel>()

    private lateinit var binding: ActivityBasketBinding
    private var modalOn : Boolean = false

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
            if( binding.orderBtn.isClickable) checkAndDisplayModal()
        }

        binding.basketModalCloseBtn.setOnClickListener {
            resetCardAnim()
        }

        resetCardAnim()

    }

    private fun checkAndDisplayModal(){
        modalOn = true
        binding.basketOverlayDark.visibility = View.VISIBLE
        binding.animationLoginHolder.visibility = View.VISIBLE
        //Test connexion
        if(userPreferences.loadSharedLoggedIn() == true){
            binding.textViewBasketModal.text= "Vous êtes bien connecté"
            binding.btnBasketModal.text = "Valider ma commande"
            binding.animationYes.visibility = View.VISIBLE
            binding.animationYes.playAnimation()
            binding.btnBasketModal.setOnClickListener {
                //Loading animation display (burger that builds up)
                binding.animationYes.visibility = View.GONE
                binding.animationLoad.visibility = View.VISIBLE
                binding.animationLoad.playAnimation()

                val apiService : APIcallsService by inject()
                val user = userPreferences.readUser()
                if(user != null){
                    val idUser = user.id
                    apiService.makeOrder(idUser.toString(), BasketData(basketService.getItems())).observe(this){
                        if(it.isEmpty()){
                            Log.w("BasketActivity", "Null list")
                            binding.animationLoad.visibility = View.GONE
                            binding.animationFail.visibility = View.VISIBLE
                            binding.animationFail.playAnimation()
                            Toast.makeText(applicationContext, "Erreur lors de la commande", Toast.LENGTH_SHORT).show()
                            binding.textViewBasketModal.text = "Erreur serveur lors de la la commande ( :no_response )"
                        }else{
                            Toast.makeText(applicationContext, "Commande effectuée", Toast.LENGTH_SHORT).show()
                            //OK animation display (big thumbs up !)
                            binding.animationLoad.visibility = View.GONE
                            binding.animationOk.visibility = View.VISIBLE
                            binding.animationOk.playAnimation()

                            basketService.clearItems()
                            loadPlats()

                            binding.textViewBasketModal.text = "Commande effectuée avec brio !"
                            binding.btnBasketModal.text = "Afficher mes commandes"
                            binding.btnBasketModal.setOnClickListener {
                                //Load orders view
                                TODO()
                            }
                        }
                    }
                }else{
                    Log.w("BasketActivity", "Null user in preferences : No id")
                    Toast.makeText(applicationContext, "Erreur lors de la commande", Toast.LENGTH_SHORT).show()
                    binding.animationLoad.visibility = View.GONE
                    binding.animationFail.visibility = View.VISIBLE
                    binding.animationFail.playAnimation()
                    binding.textViewBasketModal.text = "Erreur client sur la commande ( :no_uid )"
                }
            }
        }else{
            binding.textViewBasketModal.text= "Vous n'êtes pas identifié !"
            binding.btnBasketModal.text = "Me connecter"
            binding.animationNope.visibility = View.VISIBLE
            binding.animationNope.playAnimation()
            binding.btnBasketModal.setOnClickListener {
                this.startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        }
    }

    override fun onResume() {
        if(modalOn == true){
            resetCardAnim()
            checkAndDisplayModal()
        }
        super.onResume()
    }

    private fun resetCardAnim(){
        modalOn = false
        binding.basketOverlayDark.visibility = View.GONE
        binding.animationYes.imageAssetsFolder = "images/"
        binding.animationNope.imageAssetsFolder = "images/"
        binding.animationYes.visibility = View.GONE
        binding.animationNope.visibility = View.GONE
        binding.animationLoad.visibility = View.GONE
        binding.animationOk.visibility = View.GONE
        binding.animationFail.visibility = View.GONE
        binding.animationLoginHolder.visibility = View.GONE

    }

    private fun loadPlats(){
        val price = basketService.getTotalPrice().toString() + "€"
        binding.totalBasket.text = "Total : ${price}"
        val nbArticlesInt = basketService.getItemsCount()
        val nbArticles= nbArticlesInt.toString()
        binding.nbArticles.text = "Pour ${nbArticles} articles dans le panier"
        displayCategories(basketService.getItems())
        if(nbArticlesInt>0){
            binding.orderBtn.setAlpha(1.0f);
        }else{
            binding.orderBtn.setAlpha(.5f);
        }
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
        },{
            basketService.removeItem(it)
            loadPlats()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Category Activity Destroyed")

    }

}
