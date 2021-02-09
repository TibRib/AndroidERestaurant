package fr.isen.simon.androiderestaurant.models

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import java.io.File
import kotlin.collections.ArrayList

/**
 * Basket Service - interface
 */
interface BasketService {
    fun getItemsCount() : Int
    fun getTotalPrice() : Float
    fun appendBasket(plat : Plat)
    fun removeItem(plat : Plat)
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
    private var jsonPath : String = context.cacheDir.absolutePath + "/basket.bin"

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

    override fun removeItem(plat: Plat) {
        basketData.items.remove(plat)
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
            //Encoding to Base64 Bytes for security
            val encoded = Base64.encode(jsonObject.toString().toByteArray(Charsets.UTF_8), Base64.NO_PADDING)
            file.writeBytes(encoded) // Writing as a binary
        }
    }

    override fun loadBasket(filepath: String) {
        val file = File(filepath)
        if(file.exists()) {
            //Read the binary
            val read : ByteArray = file.readBytes()
            //Decode the bytes
            val decoded : ByteArray = Base64.decode(read,Base64.NO_PADDING)
            val string = decoded.toString(Charsets.UTF_8)
            //Decode the resulting string
            val data: BasketData = Gson().fromJson(string, BasketData::class.java)

            this.basketData.items = data.items
        }
    }
}