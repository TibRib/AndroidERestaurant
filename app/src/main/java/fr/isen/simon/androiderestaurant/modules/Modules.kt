package fr.isen.simon.androiderestaurant

import fr.isen.simon.androiderestaurant.models.*
import fr.isen.simon.androiderestaurant.services.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module{
    // Defines the basket singleton
    single { BasketData() }
    single{ BasketServiceImpl(get(), androidContext()) as BasketService }

    single{ APIcallsServiceImpl( androidContext()) as APIcallsService }

    viewModel{ UserPreferencesViewModel( androidContext()) }

}