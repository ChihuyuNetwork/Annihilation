package love.chihuyu.annihilation

import love.chihuyu.annihilation.game.MineHandler
import love.chihuyu.annihilation.game.PlacedBlockRegistry
import org.bukkit.plugin.java.JavaPlugin

class AnnihilationPlugin: JavaPlugin() {
    companion object {
        lateinit var AnnihilationPlugin: JavaPlugin
    }

    init {
        AnnihilationPlugin = this
    }

    override fun onEnable() {
        super.onEnable()

        server.pluginManager.registerEvents(MineHandler, this)
        server.pluginManager.registerEvents(PlacedBlockRegistry, this)
    }
}