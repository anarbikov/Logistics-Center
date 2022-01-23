import kotlinx.coroutines.channels.Channel

abstract class UnloadingPoints {
    abstract val name: String
    abstract var isAvailable: Boolean
    abstract val number: Int
    abstract val channel: Channel<Material>
}

val channel1 = Channel<Material>()
val channel2 = Channel<Material>()
val channel3 = Channel<Material>()

object UnloadingPoint1 : UnloadingPoints() {
    override var isAvailable = true
    override val name = "Unloading point #1"
    override val number = 0
    override val channel = channel1
}

object UnloadingPoint2 : UnloadingPoints() {
    override var isAvailable = true
    override val name = "Unloading point #2"
    override val number = 1
    override val channel = channel2
}

object UnloadingPoint3 : UnloadingPoints() {
    override var isAvailable = true
    override val name = "Unloading point #3"
    override val number = 2
    override val channel = channel3
}

