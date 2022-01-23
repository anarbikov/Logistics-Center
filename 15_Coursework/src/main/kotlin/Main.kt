import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun main() {
    runBlocking {
        val a = Warehouse()
        launch {
            while (true) {
                a.channelSelectionAndUnload()
                delay(3000)
            }
        }
        launch {
            while (true) {
                a.dispatchGoods()
                delay(5000)
            }

        }

        }
        }

