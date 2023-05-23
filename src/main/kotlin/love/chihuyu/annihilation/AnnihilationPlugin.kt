package love.chihuyu.annihilation

import com.sk89q.worldedit.bukkit.WorldEditPlugin
import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.command.impl.*
import love.chihuyu.annihilation.listener.AnnihilationListener
import love.chihuyu.annihilation.listener.CancelListener
import love.chihuyu.annihilation.map.AnnihilationMap
import love.chihuyu.annihilation.map.AnnihilationMapManager
import love.chihuyu.annihilation.map.ProtectedZone
import net.citizensnpcs.api.CitizensAPI
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class AnnihilationPlugin : JavaPlugin() {
    companion object {
        val prefix = "${ChatColor.GOLD}[Anni]${ChatColor.RESET}"
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

        listOf(
            MapConfigCommand,
            SetMapCommand,
            ShuffleCommand,
            StartCommand,
            KillCommand
        ).forEach(Command::register)

        listOf(
            AnnihilationListener,
            CancelListener
        ).forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }

    override fun onDisable() {
        super.onDisable()
        CitizensAPI.getNPCRegistry().deregisterAll()
    }
}
