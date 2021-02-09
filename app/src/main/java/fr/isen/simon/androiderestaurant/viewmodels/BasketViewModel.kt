package fr.isen.simon.androiderestaurant.viewmodels

import android.content.Context
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import fr.isen.simon.androiderestaurant.models.BasketData
import fr.isen.simon.androiderestaurant.models.Plat
import java.io.File
import kotlin.collections.ArrayList

class BasketViewModel(
    private val basketData : BasketData,
    private val context : Context
): ViewModel() {
    private var jsonPath : String = context.cacheDir.absolutePath + "/basket.bin"

    private val mListSize : MutableLiveData<Int> = MutableLiveData()

    val listSize : LiveData<Int>
        get() = mListSize

    init{ //Constructeur : chargement auto de la sauvegarde JSON
        loadBasket(jsonPath)
        mListSize.postValue(getItemsCount())
    }

    fun getItemsCount(): Int {
        return basketData.items.size
    }

    fun getItems(): ArrayList<Plat> {
        return basketData.items
    }

    fun getTotalPrice(): Float {
        var sum = 0.0f
        basketData.items.forEach(action = {
            sum += it.getPrice()
        })
        return sum
    }

    fun appendBasket(plat : Plat) {
        basketData.items.add(plat)
        saveBasket(jsonPath)
        mListSize.postValue(getItemsCount())
    }

    fun removeItem(plat: Plat) {
        basketData.items.remove(plat)
        saveBasket(jsonPath)
        mListSize.postValue(getItemsCount())
    }

    fun clearItems() {
        basketData.items.clear()
        saveBasket(jsonPath)
        mListSize.postValue(getItemsCount())

    }

    fun saveBasket(filepath: String) {
        if(!filepath.isNullOrEmpty()) {
            val file = File(filepath)
            val jsonObject = Gson().toJson(basketData)
            //Encoding to Base64 Bytes for security
            val encoded = Base64.encode(jsonObject.toString().toByteArray(Charsets.UTF_8), Base64.NO_PADDING)
            file.writeBytes(encoded) // Writing as a binary
        }
    }

    fun loadBasket(filepath: String) {
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