package love.chihuyu.annihilation.game

import org.bukkit.configuration.serialization.ConfigurationSerializable

class ProtectedZone(val x: IntRange, val y: IntRange) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "x" to x,
            "y" to y
        )
    }
}
