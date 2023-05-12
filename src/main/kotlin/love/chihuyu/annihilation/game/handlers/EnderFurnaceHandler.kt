package love.chihuyu.annihilation.game.handlers

import love.chihuyu.annihilation.game.AnnihilationGameManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory
import java.util.*

object EnderFurnaceHandler: Listener {

    private val enderFurnaces = mutableMapOf<UUID, Inventory>()

    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        val block = e.clickedBlock
        val player = e.player
        val furnace = enderFurnaces[player.uniqueId]
        val currentGame = AnnihilationGameManager.currentGame

        if (block.type == Material.FURNACE && currentGame != null && block.location in currentGame.map.enderFurnaces) {
            val inventory = enderFurnaces[player.uniqueId] ?: Bukkit.createInventory(player, InventoryType.FURNACE, "Ender Furnace")
            if (furnace == null) enderFurnaces[player.uniqueId] = inventory
            e.isCancelled = true
            player.openInventory(inventory)
        }
    }
}