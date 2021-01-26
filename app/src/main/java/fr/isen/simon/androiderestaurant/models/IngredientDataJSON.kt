package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IngredientDataJSON(
    @SerializedName
        ("name_fr") val name : String
) : Serializable{}
