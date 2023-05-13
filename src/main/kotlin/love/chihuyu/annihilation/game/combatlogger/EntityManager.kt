package love.chihuyu.annihilation.game.combatlogger

import love.chihuyu.timerapi.TimerAPI
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

object EntityManager: Listener {

    private val combatLoggers = mutableMapOf<UUID, Player>()

    @EventHandler
    fun onLeft(e: PlayerQuitEvent) {
        val player = e.player

        val logger = player.world.spawnEntity(player.location, EntityType.PLAYER) as Player
        logger.health = .5
        logger.inventory.contents = player.inventory.contents
        combatLoggers[player.uniqueId] = logger

        TimerAPI.build("combatlogger-autoremove", 15, 20) {
            if (!logger.isDead) logger.remove()
        }
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        val logger = combatLoggers[player.uniqueId]

        if (logger != null) {
            if (logger.isDead) {
                player.inventory.clear()
            } else {
                logger.remove()
                combatLoggers.remove(player.uniqueId)
            }
        }
    }
}