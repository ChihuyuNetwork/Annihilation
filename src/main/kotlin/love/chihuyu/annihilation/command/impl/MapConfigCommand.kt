package love.chihuyu.annihilation.command.impl

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.WorldEditAPI
import love.chihuyu.annihilation.command.Command
import love.chihuyu.annihilation.map.AnnihilationMap
import love.chihuyu.annihilation.map.AnnihilationMapManager
import love.chihuyu.annihilation.map.ProtectedZone
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.math.roundToInt

object MapConfigCommand: Command("mapconfig") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player || args.isEmpty()) return

        when (args[0]) {
            "add" -> {
                if (args.size < 2) return
                val id = args[1]
                AnnihilationMapManager.cachedMaps[id] = AnnihilationMap(id)
                sender.sendMessage("Map added: $id")
            }
            "displayname" -> {
                if (args.size < 3) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val displayName = args[2]
                AnnihilationPlugin.logger.info("set: ${AnnihilationMapManager.cachedMaps}")
                map.displayName = displayName
                sender.sendMessage("Map display name changed: $displayName")
            }
            "teams" -> {
                if (args.size < 4) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val teams = args.drop(2).map { ChatColor.valueOf(it) }.toMutableList()
                map.teams = teams
                sender.sendMessage("Map teams changed: ${teams.joinToString(", ") { it.name }}")
            }
            "add-enderfurnace" -> {
                if (args.size < 2) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val targetBlock = sender.getTargetBlock(setOf(Material.AIR), 3)
                if (targetBlock.type != Material.FURNACE) return
                map.enderFurnaces.add(targetBlock.location)
                sender.sendMessage("Map ender furnace added")
            }
            "remove-enderfurnace" -> {
                if (args.size < 2) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val targetBlock = sender.getTargetBlock(setOf(Material.AIR), 3)
                if (targetBlock.type != Material.FURNACE) return
                map.enderFurnaces.remove(targetBlock.location)
                sender.sendMessage("Map ender furnace removed")
            }
            "nexus" -> {
                if (args.size < 3) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val team = ChatColor.valueOf(args[2])
                val targetBlock = sender.getTargetBlock(setOf(Material.AIR), 3)
                if (targetBlock.type != Material.ENDER_STONE) return
                map.nexusLocations[team] = targetBlock.location
                sender.sendMessage("Map nexus set")
            }
            "add-spawn" -> {
                if (args.size < 3) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val team = ChatColor.valueOf(args[2])
                map.spawns[team] = (map.spawns[team] ?: mutableListOf()).plus(sender.location.apply {
                    x = x.roundToInt().toDouble()
                    y = y.roundToInt().toDouble()
                    z = z.roundToInt().toDouble()
                }).toMutableList()
                sender.sendMessage("Map spawns added")
            }
            "remove-spawn" -> {
                if (args.size < 3) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val team = ChatColor.valueOf(args[2])
                map.spawns[team] = (map.spawns[team] ?: mutableListOf()).minus(sender.location.apply {
                    x = x.roundToInt().toDouble()
                    y = y.roundToInt().toDouble()
                    z = z.roundToInt().toDouble()
                }).toMutableList()
                sender.sendMessage("Map spawns removed")
            }
            "add-protectedzone" -> {
                if (args.size < 2) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val region = WorldEditAPI.getSelection(sender)
                map.protectedZone += ProtectedZone(
                    region.minimumPoint.x.roundToInt(),
                    region.maximumPoint.x.roundToInt(),
                    region.minimumPoint.z.roundToInt(),
                    region.maximumPoint.z.roundToInt()
                )
                sender.sendMessage("Map protectedzone added")
            }
            "remove-protectedzone" -> {
                if (args.size < 2) return
                val map = AnnihilationMapManager.cachedMaps[args[1]]!!
                val region = WorldEditAPI.getSelection(sender)
                map.protectedZone -= ProtectedZone(
                    region.minimumPoint.x.roundToInt(),
                    region.maximumPoint.x.roundToInt(),
                    region.minimumPoint.z.roundToInt(),
                    region.maximumPoint.z.roundToInt()
                )
                sender.sendMessage("Map protectedzone added")
            }
        }

        AnnihilationMapManager.save()
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}