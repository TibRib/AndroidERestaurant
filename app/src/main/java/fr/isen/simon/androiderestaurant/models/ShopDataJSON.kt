package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShopDataJSON(
    @SerializedName
        ("name_fr") val name : String,
    @SerializedName
        ("items") val items : ArrayList<Plat>
) : Serializable
