package love.chihuyu.annihilation.game.nexus

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.game.AnnihilationGameManager
import love.chihuyu.annihilation.game.scoreboard.AnnihilationScoreboardManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object NexusHandler : Listener {

    @EventHandler
    fun onNexus(e: BlockBreakEvent) {
        val block = e.block
        val currentGame = AnnihilationGameManager.currentGame

        if (block.type != Material.ENDER_STONE || currentGame == null || currentGame.currentPhase.int < 2) return

        val player = e.player
        val team = currentGame.map.nexusLocations.toList().first { it.second == block.location }.first
        val mainScoreboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard

        currentGame.nexus[team] = currentGame.nexus[team]!!.dec()
        AnnihilationPlugin.server.broadcastMessage("$prefix ${mainScoreboard.getPlayerTeam(player).prefix}${player.displayName}${ChatColor.RESET} attacked ${team}Nexus (${currentGame.nexus[team]})")
        AnnihilationScoreboardManager.updateAll(currentGame)
        if (currentGame.nexus[team] == 0) {
            block.type = Material.BEDROCK
            AnnihilationPlugin.server.broadcastMessage("$prefix ${team}${mainScoreboard.getTeam(team.name).name}${ChatColor.RESET} Destroyed")
        }

        if (currentGame.nexus.filter { it.value != 0 }.size < 2) {
            AnnihilationGameManager.end()
        }
    }
}
