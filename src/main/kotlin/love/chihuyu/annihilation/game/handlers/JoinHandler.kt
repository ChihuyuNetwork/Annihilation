package love.chihuyu.annihilation.game.handlers

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
    }
}