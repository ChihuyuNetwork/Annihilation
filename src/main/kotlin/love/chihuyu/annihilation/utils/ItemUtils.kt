package love.chihuyu.annihilation.utils

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta

object ItemUtils {

    fun createEnchantBook(
        name: String? = null,
        amount: Int? = null,
        lore: List<String>? = null,
        enchantments: Map<Enchantment, Int>? = null,
        flags: List<ItemFlag>? = null,
        storedEnchants: Map<Enchantment, Int>? = null,
        unbreakable: Boolean = false
    ): ItemStack {
        val item = create(
            Material.ENCHANTED_BOOK, name, amount, lore, enchantments, flags
        )
        val meta = item.itemMeta as EnchantmentStorageMeta
        storedEnchants?.forEach { meta.addStoredEnchant(it.key, it.value, true) }
        item.itemMeta = meta
        return item
    }

    fun createLeatherArmor(
        material: Material,
        name: String? = null,
        amount: Int? = null,
        lore: List<String>? = null,
        enchantments: Map<Enchantment, Int>? = null,
        flags: List<ItemFlag>? = null,
        unbreakable: Boolean = false,
        color: Color? = null
    ): ItemStack {
        val item = ItemStack(material)
        if (amount != null) item.amount = amount

        val meta = item.itemMeta as? LeatherArmorMeta ?: return item
        if (name != null) meta.displayName = name
        if (lore != null) meta.lore = lore
        if (color != null) meta.color = color

        flags?.forEach { meta.addItemFlags(it) }

        meta.spigot().isUnbreakable = unbreakable

        item.itemMeta = meta
        enchantments?.forEach { item.addUnsafeEnchantment(it.key, it.value) }
        return item
    }

    fun createSkull(
        name: String? = null,
        amount: Int? = null,
        lore: List<String>? = null,
        enchantments: Map<Enchantment, Int>? = null,
        flags: List<ItemFlag>? = null,
        unbreakable: Boolean = false,
        owner: String? = null
    ): ItemStack {
        val item = ItemStack(Material.SKULL_ITEM)
        item.durability = 3
        if (amount != null) item.amount = amount

        val meta = item.itemMeta as? SkullMeta ?: return item
        if (name != null) meta.displayName = name
        if (lore != null) meta.lore = lore

        flags?.forEach { meta.addItemFlags(it) }

        meta.spigot().isUnbreakable = unbreakable

        if (owner != null) meta.setOwner(owner)

        item.itemMeta = meta
        enchantments?.forEach { item.addUnsafeEnchantment(it.key, it.value) }
        return item
    }

    fun create(
        material: Material,
        name: String? = null,
        amount: Int? = null,
        lore: List<String>? = null,
        enchantments: Map<Enchantment, Int>? = null,
        flags: List<ItemFlag>? = null,
        unbreakable: Boolean = false
    ): ItemStack {
        val item = ItemStack(material)
        if (amount != null) item.amount = amount

        val meta = item.itemMeta ?: return item
        if (name != null) meta.displayName = name
        if (lore != null) meta.lore = lore

        flags?.forEach { meta.addItemFlags(it) }

        meta.spigot().isUnbreakable = unbreakable

        item.itemMeta = meta
        enchantments?.forEach { item.addUnsafeEnchantment(it.key, it.value) }
        return item
    }
}