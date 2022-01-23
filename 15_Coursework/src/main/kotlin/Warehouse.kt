import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class Warehouse {
    private val storagedFood: MutableList<Material> = mutableListOf()
    private val storagedSmall: MutableList<Material> = mutableListOf()
    private val storagedMedium: MutableList<Material> = mutableListOf()
    private val storagedBig: MutableList<Material> = mutableListOf()

    suspend fun channelSelectionAndUnload() {
        when {
            UnloadingPoint1.isAvailable -> receiveGoodsFromTruck(UnloadingPoint1)
           UnloadingPoint2.isAvailable -> receiveGoodsFromTruck(
                UnloadingPoint2
            )
            UnloadingPoint3.isAvailable -> {
                receiveGoodsFromTruck(UnloadingPoint3)
            }
            else -> {
                println("All channels are locked, waiting for the available Unloading point ")
                delay(2500)
                println("Trying to unload the truck again ")
                channelSelectionAndUnload()
            }
        }
    }

    private fun receiveGoodsFromTruck(currentPoint: UnloadingPoints) {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        val a = TruckGeneratorIncome()
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

    suspend fun dispatchGoods() {
        val scope10 = CoroutineScope(Job() + Dispatchers.Default)
        val scope20 = CoroutineScope(Job() + Dispatchers.Default)
        val truck = TruckGeneratorOutcome()
        val currentPoint = selectChannelForLoading()
        val truckType = when (Random.nextInt(1, 3)) {
            1 -> NineTonTruck()
            2 -> TenTonTruck()
            else -> TenTonTruck()
        }
        val goodsType = when (Random.nextInt(0, 5)) {
            0 -> storagedFood
            1 -> storagedSmall
            2 -> storagedMedium
            3 -> storagedBig
            else -> storagedFood
        }
        goodsType.shuffle()

        val printGoodsType = when (goodsType) {
            storagedFood -> "Food goods category"
            storagedSmall -> "Small goods category"
            storagedMedium -> "Medium goods category"
            storagedBig -> "Big goods category"
            else -> "Initialization error"
        }
        print("New truck arrived to ${currentPoint.name}. The truck has ${truckType.weightCapacity} kg capacity. It needs to load $printGoodsType.\n")
        currentPoint.isAvailable = false
        println("Filling the truck with $printGoodsType has started, Loading point ${currentPoint.number + 1} is locked now ")
        val filledTruck = addGoods(truck = truckType, goodsType = goodsType)    // Defining goods for delivery before loading
        scope10.launch {
            for (i in 0 until filledTruck.goodsOnBoard.size) {
                currentPoint.channel.send(filledTruck.goodsOnBoard[i])
            }
        }
        scope20.launch {
            truck.loadGoodsAndLeave(currentPoint, filledTruck.goodsOnBoard,loadedWeight = truckType.currentWeightLoaded,capacity= truckType.weightCapacity)
            currentPoint.isAvailable = true
        }
        }

    private suspend fun selectChannelForLoading(): LoadingPoints {
        var currentPoint: LoadingPoints = LoadingPoint1
        when {
            LoadingPoint1.isAvailable -> currentPoint = LoadingPoint1
            LoadingPoint2.isAvailable -> currentPoint = LoadingPoint2
            LoadingPoint3.isAvailable -> currentPoint = LoadingPoint3
            LoadingPoint4.isAvailable -> currentPoint = LoadingPoint4
            LoadingPoint5.isAvailable -> currentPoint = LoadingPoint5
            else -> {
                println("All Loading points are busy, waiting for the available Loading point")
                delay(4500)
                selectChannelForLoading()
            }
            }
        return currentPoint
    }
    private val mutex = Mutex()
    private suspend fun addGoods(truck:Trucks, goodsType: MutableList<Material>): Trucks {
        mutex.withLock {
        while (true) {
            if (goodsType.size > 0) {
                if (truck.currentWeightLoaded + goodsType.last().materialWeight <= truck.weightCapacity) {
                    truck.goodsOnBoard.add(goodsType.last())
//                    println("${goodsType.last()} loaded to truck. Current capacity ${truck.currentWeightLoaded} from ${truck.weightCapacity}")
                    truck.currentWeightLoaded += goodsType.last().materialWeight
                    goodsType.removeLast()
                }
                else break
            }
            else {
                println("The storage is empty yet, waiting for filling the stock")
                delay(5000)
                continue
            }
        }
        }
        return truck
    }
}
