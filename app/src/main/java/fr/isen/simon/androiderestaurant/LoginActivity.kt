package fr.isen.simon.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonElement
import fr.isen.simon.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.simon.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.simon.androiderestaurant.models.RegisterDataResponseJSON
import fr.isen.simon.androiderestaurant.services.APIcallsService
import fr.isen.simon.androiderestaurant.services.UserPreferencesService
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val userPreferences by inject<UserPreferencesService>()
    private val apiService by inject<APIcallsService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

    private fun callLoginService(email: String,pass: String){
        apiService.loginUser(email,pass).observeForever {
            userPreferences.setUserLoggedIn(true)
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