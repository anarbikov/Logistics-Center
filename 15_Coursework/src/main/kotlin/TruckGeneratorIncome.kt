import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.random.Random
class TruckGeneratorIncome {

    private val allGoodsListNonFood = mutableListOf(Goods.Bricks, Goods.Cement, Goods.Nails, Goods.Laptop,
        Goods.Cartridges, Goods.Monitor, Goods.Fridge, Goods.Cooker, Goods.Washer)
    private val allGoodsListFood = mutableListOf(Goods.Milk, Goods.Bread, Goods.Potato, Goods.Beans)
    val truck = generateFilledTruckForUnloading()

    private fun generateFilledTruckForUnloading(): Trucks {   //  generating truck with random goods qty, on random truck type,
        lateinit var generatedTruck: Trucks
        when (Random.nextInt(0,4)) {
            0 -> generatedTruck = EuroTruck()
            1 -> generatedTruck = TenTonTruck()
            2 -> generatedTruck = NineTonTruck()
            3 -> generatedTruck = TwelveTonTruck()
        }
        when (Random.nextInt(0,4)){
            0 -> generatedTruck.containsFood = true  // food truck
            1 -> generatedTruck.containsFood = false   // non-food truck
            2 -> generatedTruck.containsFood = false   // non-food truck
            3 -> generatedTruck.containsFood = false   // non-food truck
        }
        if (generatedTruck.containsFood) generateFoodGoods(generatedTruck) else generateNonFoodGoods(generatedTruck)
        return generatedTruck
    }


    private fun generateFoodGoods(generatedTruck: Trucks): Trucks { //  Generates food truck
        for (i in 0 until Random.nextInt(0,12)) {
            val randomFood: Material = allGoodsListFood[Random.nextInt(from = 0, until = allGoodsListFood.size)]
            if (generatedTruck.currentWeightLoaded + randomFood.materialWeight <= generatedTruck.weightCapacity) {
                generatedTruck.goodsOnBoard.add(randomFood)
                generatedTruck.currentWeightLoaded += randomFood.materialWeight
            }
                else break
        }
        generatedTruck.goodsOnBoard.sortBy { it.materialWeight }
        return generatedTruck
    }

    private fun generateNonFoodGoods (generatedTruck: Trucks): Trucks {
        for (i in 0 until Random.nextInt(0, 12)) {
            val randomGood: Material = allGoodsListNonFood[Random.nextInt(from = 0, until = allGoodsListNonFood.size)]
            if (generatedTruck.currentWeightLoaded + randomGood.materialWeight <= generatedTruck.weightCapacity) {
                generatedTruck.goodsOnBoard.add(randomGood)
                generatedTruck.currentWeightLoaded += randomGood.materialWeight
            } else break
        }
        generatedTruck.goodsOnBoard.sortBy { it.materialWeight }
        return generatedTruck
    }

    fun sendViaExactChannel(selectChannel:Channel<Material>) {
    val scope = CoroutineScope ( Job() + Dispatchers.Default)
                    if (truck.goodsOnBoard.size > 0) {
                        val q = truck
                        scope.launch {
                        for (i in 0 until q.goodsOnBoard.size) {
                            selectChannel.send(q.goodsOnBoard[i])
                            delay(q.goodsOnBoard[i].materialLoadingTime)
                            if (i == q.goodsOnBoard.size-1) scope.cancel()
                    }

                }
         }

    }
}
