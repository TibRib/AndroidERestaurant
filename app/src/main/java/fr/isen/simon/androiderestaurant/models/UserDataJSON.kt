package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDataJSON(
    @SerializedName
        ("id") val id : Int,
    @SerializedName
        ("code") val code : String,
    @SerializedName
        ("email") val email : String,
    @SerializedName
        ("firstname") val firstname : String,
    @SerializedName
        ("lastname") val lastname : String,
    @SerializedName
        ("address") val address : String
) : Serializable{}
