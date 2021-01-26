package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PriceDataJSON(
    @SerializedName
        ("price") val price : Float,
    @SerializedName
        ("size") val size : String,
) : Serializable{}
