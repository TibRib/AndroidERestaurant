package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderResponseDataJSON(
    @SerializedName
        ("data") val orders: List<OrderDataJSON>,
    @SerializedName
        ("code") val code: Int
) : Serializable{
    fun isSuccessful() : Boolean{
        return code == 200
    }

    fun extractOrders() = orders
}
