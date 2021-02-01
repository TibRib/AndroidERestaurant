package fr.isen.simon.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.simon.androiderestaurant.databinding.FragmentToolbarBinding
import fr.isen.simon.androiderestaurant.models.BasketService
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.compat.ScopeCompat.getViewModel
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel

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
        binding.toolbarTitle.text = title
        binding.toolbarItemCount.text = basketService.getItemsCount().toString()
        binding.toolbarBasketIcon.setOnClickListener {
            this.startActivity(Intent(context, BasketActivity::class.java))
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.toolbarItemCount.text = basketService.getItemsCount().toString()
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