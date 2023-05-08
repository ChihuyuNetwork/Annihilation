package love.chihuyu.annihilation.game.handlers

import love.chihuyu.annihilation.game.AnnihilationGameManager
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
        val currentGame = AnnihilationGameManager.currentGame

        if (currentGame != null && block.location in currentGame.map.enderFurnaces) {
            e.isCancelled = true
            return
        }

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
                if (block in PlacedBlockHandler.blocks || player.gameMode == GameMode.CREATIVE) {
                    PlacedBlockHandler.unregister(e)
                    return
                }
                e.isCancelled = true
                e.player.itemInHand.durability = e.player.itemInHand.durability.dec()
                TimerAPI.build(
                    "restore-mine-${System.currentTimeMillis()}",
                    if (block.type == Material.DIAMOND_ORE || block.type == Material.EMERALD_ORE) 40 else 20,
                    20,
                    0
                ) {
                    val origin = block.type
                    start {
                        if (block.type == Material.GRAVEL) {
                            player.inventory.addItem(
                                ItemStack(Material.STRING, Random.nextInt(0..2)),
                                ItemStack(Material.FLINT, Random.nextInt(0..2)),
                                ItemStack(Material.FEATHER, Random.nextInt(0..2)),
                                ItemStack(Material.ARROW, Random.nextInt(0..1))
                            )
                        } else {
                            player.inventory.addItem(*block.getFortuneDrops(tool).toTypedArray()).forEach { (_, item) ->
                                player.world.dropItemNaturally(player.location, item)
                            }
                        }
                        player.giveExp(e.expToDrop)
                        block.type = Material.BEDROCK
                    }
                    end {
                        block.type = origin
                    }
                }.run()
            }
            else -> return
        }
    }
}
