sealed class Goods  {
    object Milk: Material() {
        override val materialName: String = "Milk"
        override val materialWeight: Int = 2000
        override val materialIsFood: Boolean = true
        override val materialCategory: String = "Food"
        override val materialLoadingTime: Long = 2500
    }
    object Bread: Material() {
        override val materialName: String = "Bread"
        override val materialWeight: Int = 1500
        override val materialIsFood: Boolean = true
        override val materialCategory: String = "Food"
        override val materialLoadingTime: Long = 2600
    }
    object Potato: Material() {
        override val materialName: String = "Potato"
        override val materialWeight: Int = 3000
        override val materialIsFood: Boolean = true
        override val materialCategory: String = "Food"
        override val materialLoadingTime: Long = 2400
    }
    object Beans: Material() {
        override val materialName: String = "Beans"
        override val materialWeight: Int = 2500
        override val materialIsFood: Boolean = true
        override val materialCategory: String = "Food"
        override val materialLoadingTime: Long = 2500
    }
    object Bricks: Material() {
        override val materialName: String = "Bricks"
        override val materialWeight: Int = 4000
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Small"
        override val materialLoadingTime: Long = 2700
    }
    object Cement: Material() {
        override val materialName: String = "Cement"
        override val materialWeight: Int = 5000
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Small"
        override val materialLoadingTime: Long = 2400
    }
    object Nails: Material() {
        override val materialName: String = "Nails"
        override val materialWeight: Int = 4500
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Small"
        override val materialLoadingTime: Long = 2500
    }

    object Laptop: Material() {
        override val materialName: String = "Laptop"
        override val materialWeight: Int = 1000
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Medium"
        override val materialLoadingTime: Long = 2800
    }
    object Cartridges: Material() {
        override val materialName: String = "Cartridges"
        override val materialWeight: Int = 1500
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Medium"
        override val materialLoadingTime: Long = 2700
    }
    object Monitor: Material() {
        override val materialName: String = "Monitor"
        override val materialWeight: Int = 1900
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Medium"
        override val materialLoadingTime: Long = 2750
    }
    object Fridge: Material() {
        override val materialName: String = "Fridge"
        override val materialWeight: Int = 3000
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Big"
        override val materialLoadingTime: Long = 2200
    }
    object Cooker: Material() {
        override val materialName: String = "Cooker"
        override val materialWeight: Int = 1500
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Big"
        override val materialLoadingTime: Long = 3000
    }
    object Washer: Material() {
        override val materialName: String = "Washer"
        override val materialWeight: Int = 1500
        override val materialIsFood: Boolean = false
        override val materialCategory: String = "Big"
        override val materialLoadingTime: Long = 3200
    }
}