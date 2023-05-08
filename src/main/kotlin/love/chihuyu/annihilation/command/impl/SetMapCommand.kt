package love.chihuyu.annihilation.command.impl

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.game.AnnihilationGame
import love.chihuyu.annihilation.game.AnnihilationGameManager
import love.chihuyu.annihilation.map.AnnihilationMap
import love.chihuyu.annihilation.map.ProtectedZone
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.CommandSender

object SetMapCommand : Command("setmap") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        AnnihilationGameManager.currentGame = AnnihilationGame(
            AnnihilationMap(
                AnnihilationPlugin.config.getString("id"),
                AnnihilationPlugin.config.getString("displayName"),
                AnnihilationPlugin.config.getList("teams") as MutableList<ChatColor>,
                AnnihilationPlugin.config.getList("enderFurnaces") as MutableList<Location>,
                AnnihilationPlugin.config.getList("nexusLocations")[0] as MutableMap<ChatColor, Location>,
                AnnihilationPlugin.config.getList("spawns") as MutableMap<ChatColor, MutableList<Location>>,
                AnnihilationPlugin.config.getList("protectedZone") as MutableList<ProtectedZone>
            )
        )
        sender.sendMessage("$prefix ${ChatColor.RESET}マップを変更しました")
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String> = emptyList()
}
