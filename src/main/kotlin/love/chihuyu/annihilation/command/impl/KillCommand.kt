package love.chihuyu.annihilation.command.impl

import love.chihuyu.annihilation.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object KillCommand: Command("kill") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player) return
        sender.health = .0
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String> = emptyList()
}