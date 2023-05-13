package love.chihuyu.annihilation.game.team

import love.chihuyu.annihilation.AnnihilationPlugin
import love.chihuyu.annihilation.game.AnnihilationGameManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object AutoJoiner: Listener {

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        val currentGame = AnnihilationGameManager.currentGame
        currentGame?.map?.teams?.map { AnnihilationPlugin.AnnihilationPlugin.server.scoreboardManager.mainScoreboard.getTeam(it.name) }?.minBy { it.size }?.addPlayer(player)
    }
}