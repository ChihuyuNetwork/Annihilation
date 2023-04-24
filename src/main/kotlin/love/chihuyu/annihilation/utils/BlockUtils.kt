package love.chihuyu.annihilation.utils

import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object BlockUtils {

    fun Block.getFortuneDrops(itemStack: ItemStack): MutableCollection<ItemStack> {
        val originDrops = getDrops(itemStack)
        return if (originDrops.isEmpty()) originDrops else {
            if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                repeat(itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)) {
                    if (Random.nextInt(100) <= (100 / itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 2)) originDrops += getDrops(itemStack)
                }
            }
            originDrops ?: mutableListOf()
        }
    }
}