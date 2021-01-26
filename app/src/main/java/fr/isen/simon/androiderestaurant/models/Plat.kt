package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Plat (
    val description:String,
    val tarif:Float,
    @SerializedName
        ("name_fr") val name : String,
    @SerializedName
        ("id") val id : Int,
    @SerializedName
        ("categ_name_fr") val category : String,
    @SerializedName
        ("images") val images : ArrayList<String>

    ) : Serializable
{}