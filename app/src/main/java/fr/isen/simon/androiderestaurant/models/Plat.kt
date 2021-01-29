package fr.isen.simon.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Plat (
    @SerializedName
        ("description") val description : String,
    @SerializedName
        ("name_fr") val name : String,
    @SerializedName
        ("id") val id : Int,
    @SerializedName
        ("categ_name_fr") val category : String,
    @SerializedName
        ("images") val images : List<String>,
    @SerializedName
        ("prices") val prices : List<PriceDataJSON>,
    @SerializedName
        ("ingredients") val ingredients : List<IngredientDataJSON>,
    ) : Serializable {
        fun getPrice() = prices[0].price.toFloat()
        fun getFormattedPrice() = getPrice().toString() + "€"

        fun getThumbnail() : String?{
            return if (images.isNotEmpty() && images[0].isNotEmpty()){
                images[0]
            } else{
                null
            }
        }

        fun getAllPictures() : List<String>?{
            return if (images.isNotEmpty()){
                images.filter { it.isNotEmpty() }
            } else{
                null
            }
        }

        fun getIngredientsNames() : List<String>?{
            return if (ingredients.isNotEmpty()){
                val filtered : List<IngredientDataJSON> = ingredients.filter{ it.name.isNotEmpty() }
                filtered.map { it -> it.name  }
            } else{
                null
            }
        }

        fun ingredientsToString() : String{
           var str : String = "Liste des ingrédients : \n"
           ingredients.forEach(action = {
               str += "- "+it.name+"\n"
            })
           return str
        }
}