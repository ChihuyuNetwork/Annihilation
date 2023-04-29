package love.chihuyu.annihilation.command.impl

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationMapConfig
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.game.AnnihilationGame
import love.chihuyu.annihilation.game.AnnihilationGameManager
import love.chihuyu.annihilation.game.ProtectedZone
import love.chihuyu.annihilation.map.AnnihilationMap
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.CommandSender

object SetMapCommand : Command("setmap") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        AnnihilationGameManager.currentGame = AnnihilationGame(
            AnnihilationMap(
                AnnihilationMapConfig.getString("id"),
                AnnihilationMapConfig.getString("displayName"),
                AnnihilationMapConfig.getList("teams") as MutableList<ChatColor>,
                AnnihilationMapConfig.getList("enderFurnaces") as MutableList<Location>,
                AnnihilationMapConfig.getList("nexusLocations")[0] as MutableMap<ChatColor, Location>,
                AnnihilationMapConfig.getList("spawns") as MutableMap<ChatColor, MutableList<Location>>,
                AnnihilationMapConfig.getList("protectedZone") as MutableList<ProtectedZone>
            )
        )
        sender.sendMessage("$prefix ${ChatColor.RESET}マップを変更しました")
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String> = AnnihilationMapConfig.getConfigurationSection("maps").getKeys(false).toList()
}
