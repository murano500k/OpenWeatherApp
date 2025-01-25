sealed class DetailsType(val name: String) {
    object Precipitation : DetailsType("Precipitation")
    object Wind : DetailsType("Wind")
    object Humidity : DetailsType("Humidity")

    override fun equals(other: Any?): Boolean {
        return this === other || (other is DetailsType && this.name == other.name)
    }

    override fun hashCode(): Int = name.hashCode()

    companion object {
        fun fromName(name: String): DetailsType? {
            return when (name) {
                Precipitation.name -> Precipitation
                Wind.name -> Wind
                Humidity.name -> Humidity
                else -> null
            }
        }
    }
}
