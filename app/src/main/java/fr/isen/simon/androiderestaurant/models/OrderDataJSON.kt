package fr.isen.simon.androiderestaurant.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderDataJSON(
    @SerializedName
        ("id_sender") val idSender : Int,
    @SerializedName
        ("id_receiver") val idReceiver : Int,
    @SerializedName
        ("sender") val sender : String,
    @SerializedName
        ("receiver") val receiver : String,
    @SerializedName
        ("code") val code : String,
    @SerializedName
        ("type_msg") val typeMsg : Int,
    @SerializedName
        ("message") val message : String
) : Serializable{
    fun extractPlats() : ArrayList<Plat>{
        if(!message.isNullOrEmpty()){
           return Gson().fromJson(message, CategoryDataJSON::class.java).getPlats()
        }
        return arrayListOf()
    }
}
