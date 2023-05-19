package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective

object AnnihilationScoreboardManager {

    fun update(player: Player) {
        val mainboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard
        val game = AnnihilationGameManager.currentGame
        mainboard.objectives.forEach(Objective::unregister)

        val title = "annihilation"
        val objective = mainboard.registerNewObjective(title, "")
        val scores = mutableListOf("")
        if (game == null) {
            scores.add("           ${ChatColor.BOLD}Waiting...")
            scores.add(" ")
        } else {
            val nextPhase = (game.currentPhase.int * 600) - game.phaseTimer.elapsed
            scores.add("        ${ChatColor.STRIKETHROUGH}${ChatColor.BOLD}   ${ChatColor.RESET}${ChatColor.BOLD}Nexus${ChatColor.STRIKETHROUGH}${ChatColor.BOLD}   ")
            game.map.teams.forEach { scores.add("${ChatColor.BOLD}${game.nexus[it]} > $it${it.name}") }
            scores.add(" ")
            scores.add("${ChatColor.BOLD}Phase ${game.currentPhase.int}${ChatColor.RESET} > ${nextPhase.floorDiv(60)}:${"%02d".format(nextPhase % 60)}")
            scores.add("${ChatColor.BOLD}Map${ChatColor.RESET} > ${ChatColor.GOLD}${ChatColor.BOLD}${game.map.displayName}")
            scores.add("  ")
        }

        scores.reversed().forEachIndexed { index, score ->
            objective.getScore(score).score = index
        }

        objective.displayName = "       ${ChatColor.GOLD}${ChatColor.BOLD}Annihilation${ChatColor.RESET}       "
        objective.displaySlot = DisplaySlot.SIDEBAR
        player.scoreboard = mainboard
    }

    fun updateAll() = AnnihilationPlugin.server.onlinePlayers.forEach { update(it) }
}
