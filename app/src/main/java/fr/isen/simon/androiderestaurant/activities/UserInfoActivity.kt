package fr.isen.simon.androiderestaurant.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.simon.androiderestaurant.databinding.ActivityUserInfoBinding
import fr.isen.simon.androiderestaurant.viewmodels.UserPreferencesViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserInfoBinding
    private val userPreferences by viewModel<UserPreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        if(userPreferences.loadSharedLoggedIn() == false){
            Toast.makeText(applicationContext, "Aucun utilisateur connecté...", Toast.LENGTH_SHORT).show()
            finish()
        }

        val user = userPreferences.readUser()
        if(user != null){
            binding.textViewFirstname.text = user.firstname
            binding.textViewLastname.text = user.lastname
            binding.textViewEmail.text = user.email
            binding.textViewAddress.text = user.address
            binding.textViewId.text = user.id.toString()
        }
        binding.btnDeconnexionInfo.setOnClickListener {
            disconnect()
        }
        binding.imageButtonBack.setOnClickListener {
            finish()
        }
    }

    private fun disconnect(){
        Toast.makeText(applicationContext, "Déconnecté", Toast.LENGTH_SHORT).show()
        userPreferences.setLoggedIn(false)
        finish()
    }
}