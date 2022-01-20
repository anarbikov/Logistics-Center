sealed class Trucks (val weightCapacity:Int,val appropriateForLoading:Boolean, var goodsOnBoard:MutableList<Material>,
                     var containsFood: Boolean, var currentWeightLoaded:Int) {}

class EuroTruck: Trucks(20000, false,mutableListOf(), false,0)
class TenTonTruck: Trucks(10000, false, mutableListOf(), false,0)
class NineTonTruck: Trucks(9000, true, mutableListOf(), false,0)
class TwelveTonTruck: Trucks(12000, true, mutableListOf(), false,0)




