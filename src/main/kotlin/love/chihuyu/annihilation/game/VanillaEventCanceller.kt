package love.chihuyu.annihilation.game

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.LeavesDecayEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.weather.WeatherChangeEvent

object VanillaEventCanceller : Listener {

    @EventHandler
    fun onLeave(e: LeavesDecayEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun onWeather(e: WeatherChangeEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun onFarm(e: EntityChangeBlockEvent) {
        e.isCancelled = e.block.type == Material.SOIL
    }
}
