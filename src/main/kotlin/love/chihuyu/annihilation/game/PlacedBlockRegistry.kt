package love.chihuyu.annihilation.game

import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object PlacedBlockRegistry: Listener {
    val blocks = mutableListOf<Block>()

    // run in MineHandler#onMine(BlockBreakEvent)
    fun unregister(e: BlockBreakEvent) { if (e.player.gameMode != GameMode.CREATIVE) blocks -= e.block }

    @EventHandler
    fun register(e: BlockPlaceEvent) { if (e.player.gameMode != GameMode.CREATIVE) blocks += e.blockPlaced }
}