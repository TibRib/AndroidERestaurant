package fr.isen.simon.androiderestaurant

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import fr.isen.simon.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.simon.androiderestaurant.databinding.FragmentToolbarBinding
import fr.isen.simon.androiderestaurant.models.BasketService
import fr.isen.simon.androiderestaurant.models.BasketViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ToolbarFragment : Fragment() {
    private var title: String? = null
    private val basketService by inject<BasketService>()

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
        binding = FragmentToolbarBinding.inflate(layoutInflater, container,false)
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

    }

    override fun onResume() {
        super.onResume()
        binding.toolbarItemCount.text = basketService.getItemsCount().toString()
        checkIsLoggedIn(binding.toolbarLoginIconYes, binding.toolbarLoginIconYes)
    }

    fun checkIsLoggedIn(yesIcon : ImageView, noIcon : ImageView){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val isLoggedIn = sharedPref.getBoolean(getString(R.string.preference_logged_in), false)
        if(isLoggedIn){
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