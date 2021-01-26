package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataJSON(
    @SerializedName
        ("data") val data : ArrayList<ShopDataJSON>
) : Serializable
