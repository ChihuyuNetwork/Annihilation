package love.chihuyu.annihilation.game.handlers

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.game.AnnihilationGameManager
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object DeathHandler: Listener {

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val player = e.entity
        val currentGame = AnnihilationGameManager.currentGame
        val mainboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard

        if (currentGame != null) {
            player.setBedSpawnLocation(currentGame.map.spawns[ChatColor.valueOf(mainboard.getPlayerTeam(player).name)]?.random(), true)
        } else {
            player.setBedSpawnLocation(Location(player.world, .0, 100.0, .0), true)
        }

        player.spigot().respawn()
    }
}