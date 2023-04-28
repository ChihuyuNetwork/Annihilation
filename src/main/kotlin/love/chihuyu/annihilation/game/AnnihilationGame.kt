package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.map.AnnihilationMap
import love.chihuyu.timerapi.TimerAPI
import org.bukkit.ChatColor

class AnnihilationGame(
    teams: MutableList<ChatColor>,
    val map: AnnihilationMap
) {
    val nexus = teams.associateWith { 75 }.toMutableMap()
    val phaseTimers = mutableMapOf(
        Phase.FIRST to TimerAPI.build("phase-1-${map.displayName}", 600, 20, 0) {
            AnnihilationPlugin.server.broadcastMessage("""$prefix ${ChatColor.GOLD}フェーズが変わりました
                ${ChatColor.RESET}■${ChatColor.GOLD}■■${ChatColor.RESET}■■
                ${ChatColor.RESET}■■${ChatColor.GOLD}■${ChatColor.RESET}■■
                ${ChatColor.RESET}■■${ChatColor.GOLD}■${ChatColor.RESET}■■
                ${ChatColor.RESET}■■${ChatColor.GOLD}■${ChatColor.RESET}■■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
            """.trimMargin())
        },
        Phase.SECOND to TimerAPI.build("phase-1-${map.displayName}", 600, 20, 0) {
            AnnihilationPlugin.server.broadcastMessage("""$prefix ${ChatColor.GOLD}フェーズが変わりました
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■■■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
            """.trimMargin())
        },
        Phase.THIRD to TimerAPI.build("phase-1-${map.displayName}", 600, 20, 0) {
            AnnihilationPlugin.server.broadcastMessage("""$prefix ${ChatColor.GOLD}フェーズが変わりました
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
            """.trimMargin())
        },
        Phase.FOURTH to TimerAPI.build("phase-1-${map.displayName}", 600, 20, 0) {
            AnnihilationPlugin.server.broadcastMessage("""$prefix ${ChatColor.GOLD}フェーズが変わりました
                ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
            """.trimMargin())
        },
        Phase.LAST to TimerAPI.build("phase-1-${map.displayName}", 600, 20, 0) {
            AnnihilationPlugin.server.broadcastMessage("""$prefix ${ChatColor.GOLD}フェーズが変わりました
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■■■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
            """.trimMargin())
        },
    )
}