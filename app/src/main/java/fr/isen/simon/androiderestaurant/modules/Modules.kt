package fr.isen.simon.androiderestaurant

import fr.isen.simon.androiderestaurant.models.*
import org.koin.dsl.module

val appModule = module{
    // Defines the basket singleton
    single { BasketData() }

    single{ BasketServiceImpl(get()) as BasketService }
}