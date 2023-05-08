package love.chihuyu.annihilation.command.impl

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.game.AnnihilationGame
import love.chihuyu.annihilation.game.AnnihilationGameManager
import love.chihuyu.annihilation.map.AnnihilationMapManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

object SetMapCommand : Command("setmap") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (args.isEmpty()) return
        val map = AnnihilationMapManager.cachedMaps[args[0]] ?: return
        AnnihilationGameManager.currentGame = AnnihilationGame(map)
        sender.sendMessage("$prefix ${ChatColor.RESET}マップを変更しました")
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String> = emptyList()
}
