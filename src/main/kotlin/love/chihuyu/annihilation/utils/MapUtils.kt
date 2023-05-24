package love.chihuyu.annihilation.utils

import love.chihuyu.annihilation.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.soulboundTag
import love.chihuyu.annihilation.game.AnnihilationGameManager
import love.chihuyu.annihilation.utils.ColorUtils.convertToColor
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

object MapUtils {

    fun Block.isProtected(player: Player) = AnnihilationGameManager.currentGame?.map?.protectedZone?.any { x in it.minX..it.maxX && z in it.minZ..it.maxZ } == true && player.gameMode != GameMode.CREATIVE

    fun Player.giveSoulbounds() {
        val team = AnnihilationPlugin.AnnihilationPlugin.server.scoreboardManager.mainScoreboard.getPlayerTeam(this)
        val teamColor = ChatColor.valueOf(team.name).convertToColor()
        inventory.helmet = ItemUtils.createLeatherArmor(
            Material.LEATHER_HELMET,
            lore = listOf(soulboundTag),
            unbreakable = true,
            color = teamColor
        )
        inventory.chestplate = ItemUtils.createLeatherArmor(
            Material.LEATHER_CHESTPLATE,
            lore = listOf(soulboundTag),
            unbreakable = true,
            color = teamColor
        )
        inventory.leggings = ItemUtils.createLeatherArmor(
            Material.LEATHER_LEGGINGS,
            lore = listOf(soulboundTag),
            unbreakable = true,
            color = teamColor
        )
        inventory.boots = ItemUtils.createLeatherArmor(
            Material.LEATHER_BOOTS,
            lore = listOf(soulboundTag),
            unbreakable = true,
            color = teamColor
        )
        inventory.addItem(
            ItemUtils.create(
                Material.WOOD_SWORD,
                lore = listOf(soulboundTag)
            ),
            ItemUtils.create(
                Material.STONE_PICKAXE,
                lore = listOf(soulboundTag)
            ),
            ItemUtils.create(
                Material.STONE_AXE,
                lore = listOf(soulboundTag)
            ),
            ItemUtils.create(
                Material.STONE_SPADE,
                lore = listOf(soulboundTag)
            ),
        )
    }
}