package fr.isen.simon.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import fr.isen.simon.androiderestaurant.databinding.FragmentToolbarBinding
import fr.isen.simon.androiderestaurant.models.BasketService
import fr.isen.simon.androiderestaurant.services.UserPreferencesService
import org.koin.android.ext.android.inject


class ToolbarFragment : Fragment() {
    private var title: String? = null
    private val basketService by inject<BasketService>()
    private val userPreferences by inject<UserPreferencesService>()

    private lateinit var binding: FragmentToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString("title")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToolbarBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarTitle.text = title
        binding.toolbarItemCount.text = basketService.getItemsCount().toString()
        binding.toolbarBasketIcon.setOnClickListener {
            this.startActivity(Intent(context, BasketActivity::class.java))
        }

        binding.toolbarLoginIconNot.setOnClickListener {
            this.startActivity(Intent(context, LoginActivity::class.java))
        }
        checkIsLoggedIn(binding.toolbarLoginIconYes, binding.toolbarLoginIconNot)

    }

    override fun onResume() {
        super.onResume()
        binding.toolbarItemCount.text = basketService.getItemsCount().toString()
        checkIsLoggedIn(binding.toolbarLoginIconYes, binding.toolbarLoginIconNot)
    }

    fun checkIsLoggedIn(yesIcon: ImageView, noIcon: ImageView){
        if(userPreferences.getUserLoggedIn()){
            yesIcon.visibility = View.VISIBLE
            noIcon.visibility = View.GONE
        }else{
            yesIcon.visibility = View.GONE
            noIcon.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun new(title: String) =
            ToolbarFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                }
            }
    }
}