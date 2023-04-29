package love.chihuyu.annihilation.map

import love.chihuyu.annihilation.game.ProtectedZone
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable

class AnnihilationMap(
    val id: String,
    val displayName: String,
    val teams: MutableList<ChatColor>,
    val enderFurnaces: MutableList<Location> = mutableListOf(),
    val nexusLocations: MutableMap<ChatColor, Location> = mutableMapOf(),
    val spawns: MutableMap<ChatColor, MutableList<Location>> = mutableMapOf(),
    val protectedZone: MutableList<ProtectedZone>
) : ConfigurationSerializable {
    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "id" to id,
            "displayName" to displayName,
            "teams" to teams,
            "enderFurnaces" to enderFurnaces,
            "nexusLocations" to nexusLocations,
            "spawns" to spawns,
            "protectedZone" to protectedZone
        )
    }
}
