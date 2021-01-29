package fr.isen.simon.androiderestaurant.models

/**
 * Basket Service - interface
 */
interface BasketService {
    fun getItemsCount() : Int
    fun getTotalPrice() : Float
    fun appendBasket(plat : Plat)
    fun getItems() : ArrayList<Plat>
}

/**
 * Basket Service Implementation
 * Will use the BasketData data
 */
class BasketServiceImpl(
    private val basketData : BasketData
): BasketService {
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
    }
}