package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BasketData(
    @SerializedName
        ("items") var items : ArrayList<Plat> = arrayListOf()
) : Serializable {
}