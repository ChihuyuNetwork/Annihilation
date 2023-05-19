package love.chihuyu.annihilation.map

import org.bukkit.configuration.serialization.ConfigurationSerializable

class ProtectedZone(val minX: Int, val maxX: Int, val minZ: Int, val maxZ: Int) : ConfigurationSerializable {
    constructor(map: MutableMap<String, Any>) : this(
        map["minX"].toString().toInt(),
        map["maxX"].toString().toInt(),
        map["minZ"].toString().toInt(),
        map["maxZ"].toString().toInt()
    )

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "minX" to minX,
            "maxX" to maxX,
            "minZ" to minZ,
            "maxZ" to maxZ
        )
    }
}
