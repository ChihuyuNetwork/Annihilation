package love.chihuyu.annihilation.game.block

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object PlacedBlockHandler : Listener {

    @EventHandler
    fun unregister(e: BlockBreakEvent) {
        if (e.block in PlacedBlockRegistry.blocks || e.player.gameMode == GameMode.CREATIVE) {
            PlacedBlockRegistry.blocks -= e.block
            return
        }
    }

    @EventHandler
    fun register(e: BlockPlaceEvent) { if (e.player.gameMode != GameMode.CREATIVE) PlacedBlockRegistry.blocks += e.blockPlaced }
}
