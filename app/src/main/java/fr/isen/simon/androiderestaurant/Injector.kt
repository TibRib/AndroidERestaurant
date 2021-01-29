package fr.isen.simon.androiderestaurant

import android.app.Application
import fr.isen.simon.androiderestaurant.models.BasketService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Injector : KoinComponent{

    val basketService by inject<BasketService>()

    fun printItemCount() = println("Nb items in basket : "+basketService.getItemsCount().toString())
}