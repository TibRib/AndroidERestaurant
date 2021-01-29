package fr.isen.simon.androiderestaurant.models

data class BasketData(
    var items : ArrayList<Plat> = arrayListOf<Plat>()
) {
}