package love.chihuyu.annihilation.game

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.map.AnnihilationMap
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
                $prefix ${ChatColor.GOLD}ゲームが開始されました
                $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■${ChatColor.RESET}■■
                $prefix ${ChatColor.RESET}■■${ChatColor.GOLD}■${ChatColor.RESET}■■
                $prefix ${ChatColor.RESET}■■${ChatColor.GOLD}■${ChatColor.RESET}■■
                $prefix ${ChatColor.RESET}■■${ChatColor.GOLD}■${ChatColor.RESET}■■
                $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                """.trimMargin()
            )
        }
        tick {
            when (elapsed) {
                600L -> {
                    currentPhase = Phase.SECOND
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.GOLD}フェーズが変わりました
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■■■
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                        $prefix ${ChatColor.GOLD}・ネクサスダメージ
                        """.trimMargin()
                    )
                }
                600L * 2 -> {
                    currentPhase = Phase.THIRD
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.GOLD}フェーズが変わりました
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                        $prefix ${ChatColor.GOLD}・ダイヤモンドスポーン
                        $prefix ${ChatColor.GOLD}・ウィッチスポーン
                        """.trimMargin()
                    )
                }
                600L * 3 -> {
                    currentPhase = Phase.FOURTH
                    AnnihilationPlugin.server.broadcastMessage(
                        """
                        $prefix ${ChatColor.GOLD}フェーズが変わりました
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                        $prefix ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                        $prefix ${ChatColor.GOLD}・ショップにブレイズパウダー追加
                        $prefix ${ChatColor.GOLD}・ミッドに黒曜石バフ出現
                        """.trimMargin()
                    )
                }
            }
        }
        end {
            currentPhase = Phase.LAST
            AnnihilationPlugin.server.broadcastMessage(
                """
                $prefix ${ChatColor.GOLD}フェーズが変わりました
                $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■${ChatColor.RESET}■■■
                $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                $prefix ${ChatColor.RESET}■■■${ChatColor.GOLD}■${ChatColor.RESET}■
                $prefix ${ChatColor.RESET}■${ChatColor.GOLD}■■■${ChatColor.RESET}■
                $prefix ${ChatColor.GOLD}・2x ネクサスダメージ
                """.trimMargin()
            )
        }
    }
}
