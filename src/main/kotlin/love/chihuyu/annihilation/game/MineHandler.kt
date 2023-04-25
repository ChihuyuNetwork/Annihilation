package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.utils.BlockUtils.getFortuneDrops
import love.chihuyu.annihilation.utils.BlockUtils.isProperTool
import love.chihuyu.timerapi.TimerAPI
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random
import kotlin.random.nextInt


object MineHandler : Listener {

    @EventHandler
    fun onMine(e: BlockBreakEvent) {
        val block = e.block
        val player = e.player
        val tool = player.itemInHand ?: return

        if (!block.isProperTool(tool.type) && player.gameMode != GameMode.CREATIVE) {
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
                if (block in PlacedBlockRegistry.blocks || player.gameMode == GameMode.CREATIVE) {
                    PlacedBlockRegistry.unregister(e)
                    return
                }
                e.isCancelled = true
                TimerAPI.build(
                    "restore-mine-${System.currentTimeMillis()}",
                    if (block.type == Material.DIAMOND_ORE || block.type == Material.EMERALD_ORE) 40 else 20,
                    20,
                    0
                ) {
                    start {

                        if (block.type == Material.GRAVEL)
                            player.inventory.addItem(
                                ItemStack(Material.STRING, Random.nextInt(3..5)),
                                ItemStack(Material.FLINT, Random.nextInt(3..5)),
                                ItemStack(Material.FEATHER, Random.nextInt(3..5)),
                                ItemStack(Material.ARROW, Random.nextInt(1..4))
                            )
                        else
                            player.inventory.addItem(*block.getFortuneDrops(tool).toTypedArray()).forEach { (_, item) ->
                                player.world.dropItemNaturally(player.location, item)
                            }
                        player.sendBlockChange(block.location, Material.BEDROCK, 0)
                    }
                    end {
                        player.sendBlockChange(block.location, block.type, block.data)
                    }
                }.run()
            }
            else -> return
        }
    }
}
