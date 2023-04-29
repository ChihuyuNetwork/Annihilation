package love.chihuyu.annihilation.command.impl

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.game.AnnihilationGameManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

object ShuffleCommand : Command("shuffle") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        AnnihilationGameManager.shuffleTeam()
        sender.sendMessage("$prefix ${ChatColor.RESET}チームをシャッフルしました")
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String> = emptyList()
}
