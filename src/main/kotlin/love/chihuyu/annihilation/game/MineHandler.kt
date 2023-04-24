package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.utils.BlockUtils.getFortuneDrops
import love.chihuyu.timerapi.TimerAPI
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent


object MineHandler : Listener {

    @EventHandler
    fun onMine(e: BlockBreakEvent) {
        val block = e.block
        val player = e.player

        if (e.block.getDrops(e.player.itemInHand).isEmpty()) {
            e.isCancelled = true
            return
        }

        when (block.type) {
            Material.IRON_ORE,
            Material.LAPIS_ORE,
            Material.COAL_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.GRAVEL,
            Material.LOG,
            Material.MELON_BLOCK
            -> {
                if (player.gameMode == GameMode.CREATIVE) return
                if (block in PlacedBlockRegistry.blocks) {
                    PlacedBlockRegistry.unregister(e)
                    return
                }
                val originType = block.type
                e.isCancelled = true
                TimerAPI.build(
                    "restore-mine-${System.currentTimeMillis()}",
                    if (block.type == Material.DIAMOND_ORE || block.type == Material.EMERALD_ORE) 40 else 20,
                    20,
                    0
                ) {
                    start {
                        player.inventory.addItem(*block.getFortuneDrops(player.itemInHand).toTypedArray()).forEach { (_, item) ->
                                player.world.dropItemNaturally(player.location, item)
                            }
                        block.type = Material.BEDROCK
                    }
                    end {
                        block.type = originType
                    }
                }.run()
            }
            else -> return
        }
    }
}
