package fr.isen.simon.androiderestaurant

import fr.isen.simon.androiderestaurant.models.BasketService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Injector : KoinComponent{
    val basketService by inject<BasketService>()

    fun printItemCount() = println("Nb items in basket : "+basketService.getItemsCount().toString())
}