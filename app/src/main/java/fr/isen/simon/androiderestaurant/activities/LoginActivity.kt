package fr.isen.simon.androiderestaurant.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import fr.isen.simon.androiderestaurant.R
import fr.isen.simon.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.simon.androiderestaurant.services.APIcallsService
import fr.isen.simon.androiderestaurant.viewmodels.UserPreferencesViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val userPreferences by viewModel<UserPreferencesViewModel>()

    private val apiService by inject<APIcallsService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(userPreferences.loadSharedLoggedIn() == true){
            //Already logged in
            finish()
        }

        binding.btnGoInscription.setOnClickListener {
            this.startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        binding.btnLoginConnexion.setOnClickListener {
            var passed = true
            //Run the tests to check if inputs are valid
            val emailI = binding.loginInputEmailAddress
            val passI = binding.loginInputPassword

            if(!isValidEmail(emailI.text)){
                markAsInvalid(emailI)
                passed = false
            }else{ markAsValid(emailI)}

            if(!isValidPassword(passI.text)){
                markAsInvalid(passI)
                passed = false
            }else{ markAsValid(passI)}

            if(passed) {
                Snackbar.make(view, "Tous les champs valides, Connexion...", Snackbar.LENGTH_SHORT).show()
                callLoginService(
                    emailI.text.toString(),
                    passI.text.toString()
                )
            }else{
                Snackbar.make(view, "Champs invalides, merci de les corriger", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    //Fonction piquée sur stackoverflow
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    // Supérieur ou égal à 8 caractères
    fun isValidPassword(target: CharSequence) : Boolean{
        return !TextUtils.isEmpty(target) && target.length >= 8
    }

    override fun onResume() {
        if(userPreferences.loadSharedLoggedIn() == true){
            //Already logged in
            Toast.makeText(applicationContext, "Connecté ✔️", Toast.LENGTH_SHORT).show()
            finish()
        }
        super.onResume()
    }

    private fun callLoginService(email: String, pass: String){
        apiService.loginUser(email,pass).observe(this) { user ->
            if(user != null){
                Toast.makeText(applicationContext, "Connecté ✔️", Toast.LENGTH_SHORT).show()
                userPreferences.writeUser(user)
                userPreferences.setLoggedIn(true)
                finish()
            }else{
                Snackbar.make(binding.root, "Utilisateur ou mot de passe erroné ❌", Snackbar.LENGTH_LONG).show()
                markAsInvalid(binding.loginInputEmailAddress)
                markAsInvalid(binding.loginInputPassword)
            }
        }
    }

    private fun markAsInvalid(input: View){
        input.backgroundTintList = ContextCompat.getColorStateList(
            applicationContext,
            R.color.red_flash
        );
    }
    private fun markAsValid(input: View){
        input.backgroundTintList = ContextCompat.getColorStateList(
            applicationContext,
            R.color.green
        );
    }
}