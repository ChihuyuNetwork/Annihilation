package love.chihuyu.annihilation.game.misc

import love.chihuyu.timerapi.TimerAPI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object AutoRespawner: Listener {

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val player = e.entity

        TimerAPI.build("annihilation-respawn", 20, 1) {
            end {
                player.spigot().respawn()
            }
        }
    }
}