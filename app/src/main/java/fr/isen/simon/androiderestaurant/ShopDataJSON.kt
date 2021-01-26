package fr.isen.simon.androiderestaurant

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShopDataJSON(
    @SerializedName
        ("name_fr") val name : String
) : Serializable
