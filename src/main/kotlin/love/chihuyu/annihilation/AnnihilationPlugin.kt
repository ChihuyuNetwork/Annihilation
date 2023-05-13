package love.chihuyu.annihilation

import com.sk89q.worldedit.bukkit.WorldEditPlugin
import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.command.impl.MapConfigCommand
import love.chihuyu.annihilation.command.impl.SetMapCommand
import love.chihuyu.annihilation.command.impl.ShuffleCommand
import love.chihuyu.annihilation.command.impl.StartCommand
import love.chihuyu.annihilation.game.block.OreRecover
import love.chihuyu.annihilation.game.block.PlacedBlockHandler
import love.chihuyu.annihilation.game.block.Protecter
import love.chihuyu.annihilation.game.combatlogger.EntityManager
import love.chihuyu.annihilation.game.enderfurnace.InventoryRegistry
import love.chihuyu.annihilation.game.launchpad.Launcher
import love.chihuyu.annihilation.game.map.AnnihilationMap
import love.chihuyu.annihilation.game.map.AnnihilationMapManager
import love.chihuyu.annihilation.game.map.ProtectedZone
import love.chihuyu.annihilation.game.misc.SpawnSetter
import love.chihuyu.annihilation.game.misc.VanillaEventCanceller
import love.chihuyu.annihilation.game.misc.VanillaMessageEditor
import love.chihuyu.annihilation.game.nexus.NexusHandler
import love.chihuyu.annihilation.game.scoreboard.UpdateJoiner
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

        listOf(
            MapConfigCommand,
            SetMapCommand,
            ShuffleCommand,
            StartCommand,
        ).forEach(Command::register)

        listOf(
            EntityManager,
            SpawnSetter,
            InventoryRegistry,
            UpdateJoiner,
            Launcher,
            EntityManager,
            OreRecover,
            NexusHandler,
            PlacedBlockHandler,
            Protecter,
            VanillaEventCanceller,
            VanillaMessageEditor
        ).forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }
}
