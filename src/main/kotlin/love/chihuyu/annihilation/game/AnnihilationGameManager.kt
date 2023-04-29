package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.scoreboard.NameTagVisibility
import org.bukkit.scoreboard.Team

object AnnihilationGameManager {

    var currentGame: AnnihilationGame? = null

    fun start() {
        initScoreboardTeam()
        currentGame!!.phaseTimer.run()
    }

    fun end() {
        if (currentGame == null) return

        val wonTeam = currentGame!!.nexus.toList().sortedByDescending { it.second }[0].first
        AnnihilationPlugin.server.broadcastMessage("""
            $prefix ${ChatColor.RESET}ゲームが終了しました！
            $prefix ${wonTeam}${AnnihilationPlugin.server.scoreboardManager.mainScoreboard.getTeam(wonTeam.name).name}${ChatColor.RESET}チームの勝利です
        """.trimIndent())

        currentGame?.map?.nexusLocations?.forEach { (_, nexus) ->
            nexus.block.type = Material.ENDER_STONE
        }
        currentGame = null
    }

    private fun initScoreboardTeam() {
        val mainScoreboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard
        mainScoreboard.teams.forEach(Team::unregister)
        ChatColor.values().forEach {
            val newTeam = mainScoreboard.registerNewTeam(it.name)
            newTeam.setAllowFriendlyFire(false)
            newTeam.nameTagVisibility = NameTagVisibility.HIDE_FOR_OTHER_TEAMS
            newTeam.prefix = "$it"
        }
    }

    fun shuffleTeam() {
        if (currentGame == null) {
            print("Game hasn't initialized.")
            return
        }
        AnnihilationPlugin.server.onlinePlayers.chunked(currentGame!!.map.teams.size).forEachIndexed { index, players ->
            val mainScoreboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard
            players.forEach { mainScoreboard.getTeam(ChatColor.values()[index].name).addPlayer(it) }
        }
    }
}