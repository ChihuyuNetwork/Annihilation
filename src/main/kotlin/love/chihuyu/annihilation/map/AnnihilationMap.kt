package love.chihuyu.annihilation.map

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable

class AnnihilationMap(
    var id: String,
    var displayName: String = "",
    var teams: MutableList<ChatColor> = mutableListOf(),
    var enderFurnaces: MutableList<Location> = mutableListOf(),
    var nexusLocations: MutableMap<ChatColor, Location> = mutableMapOf(),
    var spawns: MutableMap<ChatColor, MutableList<Location>> = mutableMapOf(),
    var protectedZone: MutableList<ProtectedZone> = mutableListOf()
) : ConfigurationSerializable {

    constructor(map: MutableMap<String, Any>) : this(
        map["id"].toString(),
        map["displayName"].toString(),
        (map["teams"] as MutableList<String>).map { ChatColor.valueOf(it) }.toMutableList(),
        map["enderFurnaces"] as MutableList<Location>,
        (map["nexusLocations"] as MutableMap<String, Location>).mapKeys { ChatColor.valueOf(it.key) }.toMutableMap(),
        (map["spawns"] as MutableMap<String, MutableList<Location>>).mapKeys { ChatColor.valueOf(it.key) }.toMutableMap(),
        map["protectedZone"] as MutableList<ProtectedZone>
    )

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "id" to id,
            "displayName" to displayName,
            "teams" to teams.map(ChatColor::name),
            "enderFurnaces" to enderFurnaces,
            "nexusLocations" to nexusLocations.mapKeys { it.key.name },
            "spawns" to spawns.mapKeys { it.key.name },
            "protectedZone" to protectedZone
        )
    }
}
