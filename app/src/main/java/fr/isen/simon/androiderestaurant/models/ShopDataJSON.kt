package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShopDataJSON(
    @SerializedName
        ("data") val data : ArrayList<CategoryDataJSON>
) : Serializable {
    //Récupère la liste des plats d'une catégorie
    fun getPlatsOfCategory(categoryName : String) : ArrayList<Plat>{
        val categories = data
        categories.forEach {
            if(it.name == categoryName){
                return it.getPlats()
            }
        }
        return arrayListOf()
    }

}
