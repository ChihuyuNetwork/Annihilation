package love.chihuyu.annihilation

import love.chihuyu.annihilation.game.*
import love.chihuyu.annihilation.map.AnnihilationMap
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class AnnihilationPlugin : JavaPlugin() {
    companion object {
        val prefix = "${ChatColor.GOLD}[Anni]"
        lateinit var AnnihilationPlugin: JavaPlugin
        lateinit var AnnihilationMapConfig: YamlConfiguration
    }

    init {
        AnnihilationPlugin = this
    }

    override fun onEnable() {
        super.onEnable()

        ConfigurationSerialization.registerClass(ProtectedZone::class.java)
        ConfigurationSerialization.registerClass(AnnihilationMap::class.java)

        saveDefaultConfig()
        saveResource("maps.yml", false)

        AnnihilationMapConfig = YamlConfiguration().also {
            it.load(File(AnnihilationPlugin.dataFolder, "maps.yml"))
        }

        server.pluginManager.registerEvents(MineHandler, this)
        server.pluginManager.registerEvents(PlacedBlockRegistry, this)
        server.pluginManager.registerEvents(NexusHandler, this)
        server.pluginManager.registerEvents(VanillaEventCanceller, this)
    }
}
