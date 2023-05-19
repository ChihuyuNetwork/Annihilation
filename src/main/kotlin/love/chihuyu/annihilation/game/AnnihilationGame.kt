package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.map.AnnihilationMap
import love.chihuyu.annihilation.utils.ScoreboardUtils
import love.chihuyu.timerapi.TimerAPI
import love.chihuyu.timerapi.timer.Timer
import org.bukkit.ChatColor

class AnnihilationGame(
    val map: AnnihilationMap
) {
    var currentPhase = Phase.FIRST
    val nexus = map.teams.associateWith { 100 }.toMutableMap()
    val phaseTimer: Timer = TimerAPI.build("phaseTimer-${map.id}", 600 * 4, 20, 0) {
        start {
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
                        $prefix ${ChatColor.WHITE}${ChatColor.BOLD}Phase 1 > 2
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.YELLOW}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}█${ChatColor.RESET}████
                        $prefix ${ChatColor.RESET}██${ChatColor.YELLOW}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}・Nexus Damagable
                        """.trimIndent()
                    )
                }
                600L * 2 -> {
                    currentPhase = Phase.THIRD
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.WHITE}${ChatColor.BOLD}Phase 2 > 3
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}██${ChatColor.GOLD}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.GOLD}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.GOLD}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.GOLD}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.GOLD}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}・Spawn Diamonds
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}・Spawn Witches
                        """.trimIndent()
                    )
                }
                600L * 3 -> {
                    currentPhase = Phase.FOURTH
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.WHITE}${ChatColor.BOLD}Phase 3 > 4
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}██${ChatColor.RED}█${ChatColor.RESET}█${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.RED}█${ChatColor.RESET}█${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}██${ChatColor.RED}███${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}████${ChatColor.RED}█${ChatColor.RESET}██
                        $prefix ${ChatColor.RESET}███████
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}・Added Blaze Powder to Shop
                        $prefix ${ChatColor.RESET}${ChatColor.BOLD}・Spawn Mid Buff
                        """.trimIndent()
                    )
                }
            }
        }
        end {
            currentPhase = Phase.LAST
            AnnihilationPlugin.server.broadcastMessage(
                """
                $prefix ${ChatColor.RESET}${ChatColor.BOLD}Phase 4 > 5
                $prefix ${ChatColor.RESET}███████
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}███${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}█${ChatColor.RESET}████
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}███${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}████${ChatColor.DARK_RED}█${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}██${ChatColor.DARK_RED}███${ChatColor.RESET}██
                $prefix ${ChatColor.RESET}███████
                $prefix ${ChatColor.RESET}${ChatColor.BOLD}・2x Nexus Damage
                """.trimIndent()
            )
        }
    }
}
