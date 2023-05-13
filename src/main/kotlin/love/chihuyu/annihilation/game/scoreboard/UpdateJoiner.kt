package love.chihuyu.annihilation.game.scoreboard

import love.chihuyu.annihilation.game.AnnihilationGameManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object UpdateJoiner: Listener {

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        AnnihilationScoreboardManager.update(player, AnnihilationGameManager.currentGame)
    }
}