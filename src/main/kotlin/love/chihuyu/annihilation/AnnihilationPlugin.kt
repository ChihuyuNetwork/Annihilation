package love.chihuyu.annihilation

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import love.chihuyu.annihilation.game.MineHandler
import love.chihuyu.annihilation.game.PlacedBlockRegistry
import org.bukkit.plugin.java.JavaPlugin

class AnnihilationPlugin: JavaPlugin() {
    companion object {
        lateinit var AnnihilationPlugin: JavaPlugin
        lateinit var worldGuard: WorldGuardPlugin
        lateinit var worldEdit: WorldEdit
    }

    init {
        AnnihilationPlugin = this
    }

    override fun onEnable() {
        super.onEnable()

        server.pluginManager.registerEvents(MineHandler, this)
        server.pluginManager.registerEvents(PlacedBlockRegistry, this)

        worldEdit = WorldEdit.getInstance()
        worldGuard = WorldGuardPlugin.inst()
    }
}