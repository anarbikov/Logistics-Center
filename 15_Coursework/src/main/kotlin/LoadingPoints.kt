import kotlinx.coroutines.channels.Channel

sealed class LoadingPoints(var name: String, var isAvailable: Boolean, val number: Int, val channel: Channel<Material>) {
}
val channel10 = Channel<Material> ()
val channel20 = Channel<Material> ()
val channel30 = Channel<Material> ()
val channel40 = Channel<Material> ()
val channel50 = Channel<Material> ()

object LoadingPoint1: LoadingPoints(
    isAvailable = true,
    name = "Loading point #1",
    number = 0,
    channel = channel10,
)

object LoadingPoint2: LoadingPoints(
    isAvailable = true,
    name = "Loading point #2",
    number = 1,
    channel = channel20,
)
object LoadingPoint3: LoadingPoints(
    isAvailable = true,
    name = "Loading point #3",
    number = 2,
    channel = channel30,
)
object LoadingPoint4: LoadingPoints(
    isAvailable = true,
    name = "Loading point #4",
    number = 3,
    channel = channel40,
)
object LoadingPoint5: LoadingPoints(
    isAvailable = true,
    name = "Loading point #5",
    number = 4,
    channel = channel50,
)