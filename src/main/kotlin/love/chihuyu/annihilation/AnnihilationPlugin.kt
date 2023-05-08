package love.chihuyu.annihilation

import com.sk89q.worldedit.bukkit.WorldEditPlugin
import love.chihuyu.annihilation.command.impl.MapConfigCommand
import love.chihuyu.annihilation.command.impl.SetMapCommand
import love.chihuyu.annihilation.command.impl.ShuffleCommand
import love.chihuyu.annihilation.command.impl.StartCommand
import love.chihuyu.annihilation.game.*
import love.chihuyu.annihilation.map.AnnihilationMap
import love.chihuyu.annihilation.map.AnnihilationMapManager
import love.chihuyu.annihilation.map.ProtectedZone
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class AnnihilationPlugin : JavaPlugin() {
    companion object {
        val prefix = "${ChatColor.GOLD}[Anni]"
        lateinit var mapFile: File
        lateinit var AnnihilationPlugin: JavaPlugin
        lateinit var AnnihilationMapConfig: YamlConfiguration
        lateinit var WorldEditAPI: WorldEditPlugin

    }

    init {
        AnnihilationPlugin = this
        mapFile = File(dataFolder, "maps.yml")
    }

    override fun onEnable() {
        super.onEnable()

        ConfigurationSerialization.registerClass(ProtectedZone::class.java)
        ConfigurationSerialization.registerClass(AnnihilationMap::class.java)

        WorldEditAPI = server.pluginManager.getPlugin("WorldEdit") as WorldEditPlugin

        AnnihilationMapConfig = YamlConfiguration.loadConfiguration(mapFile)

        saveDefaultConfig()
        saveResource("maps.yml", false)
        AnnihilationMapManager.cache()

        MapConfigCommand.register()
        SetMapCommand.register()
        ShuffleCommand.register()
        StartCommand.register()

        server.pluginManager.registerEvents(MineHandler, this)
        server.pluginManager.registerEvents(PlacedBlockRegistry, this)
        server.pluginManager.registerEvents(NexusHandler, this)
        server.pluginManager.registerEvents(VanillaEventCanceller, this)
        server.pluginManager.registerEvents(ProtectedZoneHandler, this)
        server.pluginManager.registerEvents(VanillaMessageHandler, this)
        server.pluginManager.registerEvents(JoinHandler, this)
    }
}
