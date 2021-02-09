package fr.isen.simon.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.isen.simon.androiderestaurant.activities.BasketActivity
import fr.isen.simon.androiderestaurant.activities.LoginActivity
import fr.isen.simon.androiderestaurant.databinding.FragmentToolbarBinding
import fr.isen.simon.androiderestaurant.viewmodels.BasketViewModel
import fr.isen.simon.androiderestaurant.viewmodels.UserPreferencesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ToolbarFragment : Fragment() {
    private var title: String? = null
    private val basketViewModel by sharedViewModel<BasketViewModel>()
    private val userPreferences by sharedViewModel<UserPreferencesViewModel>()

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
        binding.toolbarBasketIcon.setOnClickListener {
            this.startActivity(Intent(context, BasketActivity::class.java))
        }

        binding.toolbarLoginIconNot.setOnClickListener {
            this.startActivity(Intent(context, LoginActivity::class.java))
        }


        userPreferences.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            Log.d("Fragment Toolbar", "changed isLoggedin to $isLoggedIn")
            if(isLoggedIn){
                binding.toolbarLoginIconYes.visibility = View.VISIBLE
                binding.toolbarLoginIconNot.visibility = View.GONE
            }else{
                binding.toolbarLoginIconYes.visibility = View.GONE
                binding.toolbarLoginIconNot.visibility = View.VISIBLE
            }
        }

        basketViewModel.listSize.observe(viewLifecycleOwner){ size ->
            Log.d("Fragment Toolbar", "changed basket size : ${size}")
            binding.toolbarItemCount.text = size.toString()
        }

    }

    override fun onResume() {
        binding.toolbarItemCount.text = basketViewModel.getItemsCount().toString()
        super.onResume()
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