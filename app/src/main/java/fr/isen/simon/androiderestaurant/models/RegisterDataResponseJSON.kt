package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterDataResponseJSON(
    @SerializedName
        ("data") val data: UserDataJSON?,
    @SerializedName
        ("code") val code: Int?
) : Serializable{

    fun isSuccessful() : Boolean{
        if(code != null){
            return code == 200
        }
        return false
    }

    fun extractUser() : UserDataJSON?{
        return data
    }
}
