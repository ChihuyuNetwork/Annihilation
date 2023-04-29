package love.chihuyu.annihilation.utils

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object BlockUtils {

    fun Block.isProperTool(material: Material): Boolean {
        val pickaxes = setOf(
            Material.WOOD_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLD_PICKAXE,
            Material.DIAMOND_PICKAXE
        )
        val shovels = setOf(
            Material.WOOD_SPADE,
            Material.STONE_SPADE,
            Material.IRON_SPADE,
            Material.GOLD_SPADE,
            Material.DIAMOND_SPADE
        )
        val axes = setOf(
            Material.WOOD_AXE,
            Material.STONE_AXE,
            Material.IRON_AXE,
            Material.GOLD_AXE,
            Material.DIAMOND_AXE
        )
        return when (type) {
            Material.IRON_ORE,
            Material.LAPIS_ORE,
            Material.REDSTONE_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.COAL_ORE,
            Material.QUARTZ_ORE,
            Material.GLOWING_REDSTONE_ORE -> {
                material in pickaxes
            }

            Material.DIRT,
            Material.SOIL,
            Material.GRASS,
            Material.GRAVEL -> {
                material in shovels
            }

            Material.STONE,
            Material.COBBLESTONE,
            Material.MOSSY_COBBLESTONE,
            Material.COBBLE_WALL,
            Material.COBBLESTONE_STAIRS,
            Material.STEP,
            Material.DOUBLE_STEP -> {
                material in pickaxes
            }

            Material.LOG,
            Material.LOG_2,
            Material.WOOD_STEP,
            Material.WOOD_DOUBLE_STEP,
            Material.WOOD,
            Material.BIRCH_WOOD_STAIRS,
            Material.ACACIA_STAIRS,
            Material.DARK_OAK_STAIRS,
            Material.JUNGLE_WOOD_STAIRS,
            Material.SPRUCE_WOOD_STAIRS,
            Material.WOOD_STAIRS,
            Material.FENCE,
            Material.FENCE_GATE,
            Material.ACACIA_FENCE,
            Material.BIRCH_FENCE,
            Material.DARK_OAK_FENCE,
            Material.JUNGLE_FENCE,
            Material.SPRUCE_FENCE,
            Material.ACACIA_FENCE_GATE,
            Material.BIRCH_FENCE_GATE,
            Material.DARK_OAK_FENCE_GATE,
            Material.JUNGLE_FENCE_GATE,
            Material.SPRUCE_FENCE_GATE,
            Material.ACACIA_DOOR,
            Material.BIRCH_DOOR,
            Material.DARK_OAK_DOOR,
            Material.JUNGLE_DOOR,
            Material.SPRUCE_DOOR,
            Material.TRAP_DOOR,
            Material.WOODEN_DOOR,
            Material.WOOD_DOOR -> {
                material in axes
            }

            Material.WOOL,
            Material.CARPET -> {
                material == Material.SHEARS
            }

            else -> true
        }
    }

    fun Block.getFortuneDrops(itemStack: ItemStack): MutableCollection<ItemStack> {
        val originDrops = getDrops(itemStack)
        return if (originDrops.isEmpty()) {
            originDrops
        } else {
            if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                repeat(itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)) {
                    if (Random.nextInt(100) <= (100 / itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 2)) {
                        originDrops += getDrops(
                            itemStack
                        )
                    }
                }
            }
            originDrops ?: mutableListOf()
        }
    }
}
