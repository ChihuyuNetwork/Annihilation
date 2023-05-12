package love.chihuyu.annihilation.game.handlers

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.game.AnnihilationGameManager
import love.chihuyu.annihilation.game.AnnihilationScoreboardManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object JoinHandler: Listener {

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        AnnihilationScoreboardManager.update(player, AnnihilationGameManager.currentGame)

        val currentGame = AnnihilationGameManager.currentGame
        currentGame?.map?.teams?.map { AnnihilationPlugin.server.scoreboardManager.mainScoreboard.getTeam(it.name) }?.minBy { it.size }?.addPlayer(player)
    }
}