package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.map.AnnihilationMap
import love.chihuyu.annihilation.utils.MapUtils.giveSoulbounds
import love.chihuyu.annihilation.utils.ScoreboardUtils
import love.chihuyu.timerapi.TimerAPI
import love.chihuyu.timerapi.timer.Timer
import org.bukkit.ChatColor
import org.bukkit.GameMode

class AnnihilationGame(
    val map: AnnihilationMap
) {
    var currentPhase = Phase.FIRST
    val nexus = map.teams.associateWith { 100 }.toMutableMap()
    var isStarted = false
    val phaseTimer: Timer = TimerAPI.build("phaseTimer-${map.id}", 600 * 4, 20, 0) {
        start {
            AnnihilationPlugin.server.onlinePlayers.forEach {
                it.inventory.clear()
                it.gameMode = GameMode.SURVIVAL
                it.foodLevel = 20
                it.health = it.maxHealth
                it.exp = 0f
                it.level = 0
                val teamSpawn = map.spawns[ChatColor.valueOf(AnnihilationPlugin.server.scoreboardManager.mainScoreboard.getPlayerTeam(it).name)]?.random()
                it.setBedSpawnLocation(teamSpawn, true)
                it.teleport(teamSpawn)
                it.giveSoulbounds()
            }

            isStarted = true
            currentPhase = Phase.FIRST
            AnnihilationPlugin.server.broadcastMessage(
                """
                $prefix ${ChatColor.WHITE}${ChatColor.BOLD}Game Start
                $prefix ${ChatColor.RESET}███████
                $prefix ${ChatColor.RESET}██${ChatColor.GREEN}██${ChatColor.RESET}███
                $prefix ${ChatColor.RESET}███${ChatColor.GREEN}█${ChatColor.RESET}███
                $prefix ${ChatColor.RESET}███${ChatColor.GREEN}█${ChatColor.RESET}███
                $prefix ${ChatColor.RESET}███${ChatColor.GREEN}█${ChatColor.RESET}███
                $prefix ${ChatColor.RESET}██${ChatColor.GREEN}███${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}███████
                """.trimIndent()
            )
        }
        tick {
            ScoreboardUtils.updateAll()

            when (elapsed) {
                600L -> {
                    currentPhase = Phase.SECOND
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.WHITE}${ChatColor.BOLD}Phase ${ChatColor.GREEN}${ChatColor.BOLD}1 ${ChatColor.RESET}> ${ChatColor.YELLOW}${ChatColor.BOLD}2
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.YELLOW}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}█${ChatColor.RESET}████
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}* Nexus Damagable
                        """.trimIndent()
                    )
                }
                600L * 2 -> {
                    currentPhase = Phase.THIRD
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.WHITE}${ChatColor.BOLD}Phase ${ChatColor.YELLOW}${ChatColor.BOLD}2 ${ChatColor.RESET}> ${ChatColor.GOLD}${ChatColor.BOLD}3
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}██${ChatColor.GOLD}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.GOLD}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.GOLD}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.GOLD}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.GOLD}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}* Spawn Diamonds
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}* Spawn Witches
                        """.trimIndent()
                    )
                }
                600L * 3 -> {
                    currentPhase = Phase.FOURTH
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.WHITE}${ChatColor.BOLD}Phase ${ChatColor.GOLD}${ChatColor.BOLD}3 ${ChatColor.RESET}> ${ChatColor.RED}${ChatColor.BOLD}4
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}██${ChatColor.RED}█${ChatColor.RESET}█${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.RED}█${ChatColor.RESET}█${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.RED}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}* Added Blaze Powder to Shop
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}* Spawn Mid Buff
                        """.trimIndent()
                    )
                }
            }
        }
        end {
            currentPhase = Phase.LAST
            AnnihilationPlugin.server.broadcastMessage(
                """
                $prefix ${ChatColor.RESET}${ChatColor.BOLD}Phase ${ChatColor.RED}${ChatColor.BOLD}4 ${ChatColor.RESET}> ${ChatColor.DARK_RED}${ChatColor.BOLD}5
                $prefix ${ChatColor.RESET}███████
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}███${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}█${ChatColor.RESET}████
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}███${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}████${ChatColor.DARK_RED}█${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}███${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}███████
                $prefix ${ChatColor.RESET}${ChatColor.BOLD}* 2x Nexus Damage
                """.trimIndent()
            )
            ScoreboardUtils.updateAll()
        }
    }
}
