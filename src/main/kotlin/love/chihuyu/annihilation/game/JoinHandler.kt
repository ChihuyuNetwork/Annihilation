package love.chihuyu.annihilation.game

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