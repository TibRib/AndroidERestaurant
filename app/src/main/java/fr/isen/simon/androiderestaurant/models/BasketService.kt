package fr.isen.simon.androiderestaurant.models

import android.content.Context
import com.google.gson.Gson
import java.io.File

/**
 * Basket Service - interface
 */
interface BasketService {
    fun getItemsCount() : Int
    fun getTotalPrice() : Float
    fun appendBasket(plat : Plat)
    fun getItems() : ArrayList<Plat>
    fun clearItems()
    fun saveBasket(filepath : String)
    fun loadBasket(filepath : String)
}

/**
 * Basket Service Implementation
 * Will use the BasketData data
 */
class BasketServiceImpl(
    private val basketData : BasketData,
    private var context : Context
): BasketService {
    private var jsonPath : String = context.cacheDir.absolutePath + "basket.json"

    init{ //Constructeur : chargement auto de la sauvegarde JSON
        loadBasket(jsonPath)
    }
    override fun getItemsCount(): Int {
        return basketData.items.size
    }

    override fun getItems(): ArrayList<Plat> {
        return basketData.items
    }

    override fun getTotalPrice(): Float {
        var sum = 0.0f
        basketData.items.forEach(action = {
            sum += it.getPrice()
        })
        return sum
    }

    override fun appendBasket(plat : Plat) {
        basketData.items.add(plat)
        saveBasket(jsonPath)
    }

    override fun clearItems() {
        basketData.items.clear()
        saveBasket(jsonPath)
    }

    override fun saveBasket(filepath: String) {
        if(!filepath.isNullOrEmpty()) {
            val file = File(filepath)
            val jsonObject = Gson().toJson(basketData)
            file.writeText(jsonObject)
        }
    }

    override fun loadBasket(filepath: String) {
        val file = File(filepath)
        if(file.exists()) {
            val read = file.readText()
            val data: BasketData = Gson().fromJson(read, BasketData::class.java)
            this.basketData.items = data.items
        }
    }
}