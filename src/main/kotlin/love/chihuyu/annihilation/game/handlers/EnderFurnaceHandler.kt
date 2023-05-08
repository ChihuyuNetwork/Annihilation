package love.chihuyu.annihilation.game.handlers

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType

object EnderFurnaceHandler: Listener {

    @EventHandler
    fun onOpen(e: InventoryOpenEvent) {
        val type = e.inventory.type

        if (type == InventoryType.FURNACE) {

        }
    }
}