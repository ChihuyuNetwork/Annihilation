package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
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
        val team = AnnihilationGameManager.currentGame!!.map.nexusLocations.toList().first { it.second == block.location }.first
        val mainScoreboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard

        AnnihilationGameManager.currentGame!!.nexus[team] = AnnihilationGameManager.currentGame!!.nexus[team]!!.dec()
        AnnihilationPlugin.server.broadcastMessage("$prefix ${ChatColor.valueOf(mainScoreboard.getTeam(player.name).name)}${player.displayName}が${team}ネクサス${ChatColor.RESET}を攻撃しました")
        if (AnnihilationGameManager.currentGame!!.nexus[team] == 0) {
            block.type = Material.BEDROCK
            AnnihilationPlugin.server.broadcastMessage("$prefix ${team}${mainScoreboard.getTeam(team.name).name}${ChatColor.RESET}が陥落しました")
        }

        if (AnnihilationGameManager.currentGame!!.nexus.filter { it.value != 0 }.size < 2) {
            AnnihilationGameManager.end()
        }
    }
}
