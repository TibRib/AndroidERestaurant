package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterDataResponseJSON(
    @SerializedName
        ("data") val data: UserDataJSON,
    @SerializedName
        ("code") val code: Int
) : Serializable{

    fun isSuccessful() : Boolean{
        return code == 200
    }

    fun extractUser() : UserDataJSON{
        return data
    }
}
