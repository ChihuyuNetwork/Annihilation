package love.chihuyu.annihilation.command.impl

import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.game.AnnihilationGameManager
import org.bukkit.command.CommandSender

object StartCommand : Command("anni-start") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        AnnihilationGameManager.start()
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String> = emptyList()
}
