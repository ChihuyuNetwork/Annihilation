package love.chihuyu.annihilation.listener

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.LeavesDecayEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.weather.WeatherChangeEvent

object CancelListener : Listener {

    @EventHandler
    private fun onLeaveDecay(e: LeavesDecayEvent) {
        e.isCancelled = true
    }

    @EventHandler
    private fun onWeatherChange(e: WeatherChangeEvent) {
        e.isCancelled = true
    }

    @EventHandler
    private fun onFarmlandBreak(e: EntityChangeBlockEvent) {
        e.isCancelled = e.block.type == Material.SOIL
    }
}
