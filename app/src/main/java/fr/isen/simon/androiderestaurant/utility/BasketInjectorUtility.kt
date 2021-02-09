package fr.isen.simon.androiderestaurant.utility

import fr.isen.simon.androiderestaurant.models.BasketService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BasketInjectorUtility() : KoinComponent {
    private val basketService by inject<BasketService>()
    fun getService() = basketService
}