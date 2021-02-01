package fr.isen.simon.androiderestaurant.models

import android.content.ClipData.Item
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BasketViewModel(private val repo: BasketService) : ViewModel() {
    var items = MutableLiveData<List<Plat>>().apply { postValue(repo.getItems()) }

    fun getItems() : LiveData<List<Plat>>{
        return items
    }

}