import kotlinx.coroutines.delay

internal class TruckGeneratorOutcome {
    suspend fun loadGoodsAndLeave(currentPoint: LoadingPoints, goodsForLoading: MutableList<Material>, loadedWeight:Int, capacity:Int) {
        val loadedGoods: MutableList<Material> = mutableListOf()
        val currentChannel = currentPoint.channel
        repeat(goodsForLoading.size) {
            val good = currentChannel.receive()
            println("${good.materialName} has been loaded to truck at ${currentPoint.name}. Loading time ${good.materialLoadingTime} millisecond.")
            loadedGoods.add(good)
            delay(good.materialLoadingTime)
        }
        println("The truck was fully loaded ($loadedWeight kg loaded, capacity $capacity kg) with the following goods:")
        for (i in loadedGoods) print("${i.materialName},")
        println(" the truck leaves the warehouse. ${currentPoint.name} is available again.")
    }
}