package fr.isen.simon.androiderestaurant


import fr.isen.simon.androiderestaurant.models.BasketData
import fr.isen.simon.androiderestaurant.viewmodels.BasketViewModel
import fr.isen.simon.androiderestaurant.services.APIcallsService
import fr.isen.simon.androiderestaurant.services.APIcallsServiceImpl
import fr.isen.simon.androiderestaurant.viewmodels.UserPreferencesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module{
    // Defines the basket singleton
    single { BasketData() }

    single{ APIcallsServiceImpl( androidContext()) as APIcallsService }

    viewModel{ UserPreferencesViewModel( androidContext()) }
    viewModel { BasketViewModel(get(), androidContext()) }
}