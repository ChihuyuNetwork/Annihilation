package love.chihuyu.annihilation.game.misc

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object VanillaMessageEditor: Listener {

    @EventHandler
    fun onKill(e: PlayerDeathEvent) {
        val dead = e.entity
        val killer = e.entity.killer
        val mainboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard

        val deadColor = mainboard.getPlayerTeam(dead).prefix

        e.droppedExp = 0

        if (killer != null) {
            val killerColor = mainboard.getPlayerTeam(killer).prefix
            e.deathMessage = "$killerColor${killer.displayName}${ChatColor.RESET} killed $deadColor${dead.displayName}"
        } else {
            e.deathMessage = "$deadColor${dead.displayName} ${ChatColor.RESET}dead"
        }
    }
}