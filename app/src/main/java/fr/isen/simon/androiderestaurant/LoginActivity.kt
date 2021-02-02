package fr.isen.simon.androiderestaurant

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import fr.isen.simon.androiderestaurant.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLoginConnexion.setOnClickListener {
            var passed = true
            //Run the tests to check if inputs are valid
            val firstNameI = binding.loginInputFirstName
            val nameI = binding.loginInputName
            val emailI = binding.loginInputEmailAddress
            val passI = binding.loginInputPassword

            if(firstNameI.text.isEmpty()){
                markAsInvalid(firstNameI)
                passed = false
            }else{ markAsValid(firstNameI)}
            if(nameI.text.isEmpty()){
                markAsInvalid(nameI)
                passed = false
            }else{ markAsValid(nameI)}
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
                setLoggedInPreferences(true)
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

    private fun setLoggedInPreferences(value: Boolean){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.preference_logged_in), value)
            apply()
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