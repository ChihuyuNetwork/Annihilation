package love.chihuyu.annihilation.game

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object ProtectedZoneHandler: Listener {

    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        val currentGame = AnnihilationGameManager.currentGame
        val block = e.block
        val player = e.player
        if (
            currentGame != null &&
            currentGame.map.protectedZone.any { block.x in it.minX..it.maxX && block.z in it.minZ..it.maxZ } &&
            player.gameMode != GameMode.CREATIVE
            )
        {
            e.isCancelled = true
            return
        }
    }

    @EventHandler
    fun onBreak(e: BlockPlaceEvent) {
        val currentGame = AnnihilationGameManager.currentGame
        val block = e.block
        val player = e.player
        if (
            currentGame != null &&
            currentGame.map.protectedZone.any { block.x in it.minX..it.maxX && block.z in it.minZ..it.maxZ } &&
            player.gameMode != GameMode.CREATIVE
            )
        {
            e.isCancelled = true
            return
        }
    }
}