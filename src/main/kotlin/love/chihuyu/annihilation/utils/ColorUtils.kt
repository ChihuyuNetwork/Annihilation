package love.chihuyu.annihilation.utils

import org.bukkit.ChatColor
import org.bukkit.Color

object ColorUtils {

    fun ChatColor.convertToColor(): Color {
        val colorMap = mapOf(
            ChatColor.RED to Color.RED,
            ChatColor.GOLD to Color.ORANGE,
            ChatColor.DARK_RED to Color.MAROON,
            ChatColor.AQUA to Color.AQUA,
            ChatColor.BLUE to Color.NAVY,
            ChatColor.GREEN to Color.GREEN,
            ChatColor.DARK_AQUA to Color.TEAL,
            ChatColor.WHITE to Color.WHITE,
            ChatColor.GRAY to Color.SILVER,
            ChatColor.YELLOW to Color.YELLOW,
            ChatColor.BLACK to Color.BLACK,
            ChatColor.DARK_GRAY to Color.GRAY,
            ChatColor.DARK_BLUE to Color.BLUE,
            ChatColor.DARK_GREEN to Color.OLIVE,
            ChatColor.DARK_PURPLE to Color.PURPLE,
            ChatColor.LIGHT_PURPLE to Color.FUCHSIA,
        )
        return colorMap[this]!!
    }
}