import kotlinx.coroutines.*

class Warehouse {
    var storagedFood: MutableList<Material> = mutableListOf()
    var storagedSmall: MutableList<Material> = mutableListOf()
    var storagedMedium: MutableList<Material> = mutableListOf()
    var storagedBig: MutableList<Material> = mutableListOf()

    suspend fun constantUnloading() {
        while (true) {
            channelSelectionAndLaunch()
            delay(3000)
        }
    }

    suspend fun channelSelectionAndLaunch() {
        when {
            UnloadingPoints.UnloadingPoint1.isAvailable -> receiveGoodsFromTruck(UnloadingPoints.UnloadingPoint1)
            UnloadingPoints.UnloadingPoint2.isAvailable -> receiveGoodsFromTruck(
                UnloadingPoints.UnloadingPoint2
            )
            UnloadingPoints.UnloadingPoint3.isAvailable -> {
                receiveGoodsFromTruck(UnloadingPoints.UnloadingPoint3)
            }
            else -> {
                println("All channels are locked, waiting for the available Unloading point ")
                delay(2500)
                println("Trying to unload the truck again ")
                channelSelectionAndLaunch()
            }
        }
    }

    private fun receiveGoodsFromTruck(currentPoint: UnloadingPoints) {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        val a = TruckGenerator()
        val b = a.truck
        println(
            "New truck arrives to Warehouse to ${currentPoint.name}. The truck's capacity: ${b.weightCapacity}kg.\n" +
                    "The total weight of loaded goods: ${b.currentWeightLoaded}.\n" +
                    "Truck contains: "
        )
        if (b.goodsOnBoard.size > 1) {
            for (i in 0 until b.goodsOnBoard.size - 1) print("${b.goodsOnBoard[i].materialName},")
            print("${b.goodsOnBoard[b.goodsOnBoard.size - 1].materialName}.")
        } else if (b.goodsOnBoard.size == 1) print("${b.goodsOnBoard[0].materialName}.")
        else println("no goods have been delivered this time")
        println("\nStarting goods receipt, ${currentPoint.name} is locked now")
        currentPoint.isAvailable = false
        scope.launch {
            a.sendViaExactChannel(currentPoint.channel)  // Sending goods via a current point's channel
            repeat(b.goodsOnBoard.size) {
                val good = currentPoint.channel.receive()
                when (good.materialCategory) {
                    "Food" -> storagedFood.add(good)
                    "Small" -> storagedSmall.add(good)
                    "Medium" -> storagedMedium.add(good)
                    "Big" -> storagedBig.add(good)
                    else -> println("GOODS WERE NOT ADDED")
                }
                println("Receipting of ${good.materialName} via Point${currentPoint.number + 1}. Time of receipt: ${good.materialLoadingTime} millisecond")
                delay(good.materialLoadingTime)
            }
            println(
                "Goods receipt has been finished. The truck leaves the warehouse and ${currentPoint.name} is " +
                        "available again. The goods were placed at stock by goods categories.\n"
            )
            currentPoint.isAvailable = true
            cancel()
        }
    }
}
