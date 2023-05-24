package love.chihuyu.annihilation.listener

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.prefix
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.soulboundTag
import love.chihuyu.annihilation.game.AnnihilationGameManager
import love.chihuyu.annihilation.utils.BlockUtils.getFortuneDrops
import love.chihuyu.annihilation.utils.BlockUtils.isProperTool
import love.chihuyu.annihilation.utils.ItemUtils
import love.chihuyu.annihilation.utils.MapUtils.giveSoulbounds
import love.chihuyu.annihilation.utils.MapUtils.isProtected
import love.chihuyu.annihilation.utils.ScoreboardUtils
import love.chihuyu.timerapi.TimerAPI
import net.citizensnpcs.api.CitizensAPI
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.Sign
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.*
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

object AnnihilationListener : Listener {
    private val placedBlocks = mutableListOf<Block>()
    private val enderFurnaces = mutableMapOf<UUID, Inventory>()

    @EventHandler
    private fun teamPrivatedChat(e: AsyncPlayerChatEvent) {
        val player = e.player
        val team = AnnihilationPlugin.server.scoreboardManager.mainScoreboard.getPlayerTeam(player)

        if (e.message[0] == '!') {
            e.format = "${ChatColor.DARK_PURPLE}[${team.name[0].uppercase()}]${ChatColor.RESET} ${player.name}: ${e.message}".removePrefix("!")
        } else {
            e.format = "${ChatColor.valueOf(team.name)}[${team.name[0].uppercase()}]${ChatColor.RESET} ${player.name}: ${e.message}"
            e.recipients.clear()
            team.entries.map { Bukkit.getOfflinePlayer(it) }.forEach { if (it.isOnline) e.recipients.add(it as Player) }
        }
    }

    @EventHandler
    private fun handleShop(e: InventoryClickEvent) {
        val player = e.whoClicked
        val inv = e.inventory ?: return
        if (inv.size != 18) return

        e.isCancelled = true

        val item = e.currentItem ?: return
        if (!item.itemMeta.hasLore()) return
        var remainCost = Integer.parseInt(item.itemMeta.lore[0].split(" ")[0].split("f")[1])

        if (player.inventory.contains(Material.GOLD_INGOT)) {
            if (player.inventory.filterNotNull().filter { it.type == Material.GOLD_INGOT }.sumOf { it.amount } < remainCost) {
                player.sendMessage("$prefix ${ChatColor.RED}You don't have enough golds!")
                return
            }
            player.inventory.filterNotNull().filter { it.type == Material.GOLD_INGOT }.forEach {
                if (remainCost == 0) return@forEach
                if (it.amount <= remainCost) {
                    remainCost -= it.amount
                    player.inventory.remove(it)
                } else {
                    it.amount = it.amount - remainCost
                    remainCost = 0
                }
            }
            player.inventory.addItem(item).forEach {
                player.world.dropItemNaturally(player.location, it.value)
            }
        }
    }

    @EventHandler
    private fun openShop(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        val state = block.state as? Sign ?: return
        when (state.getLine(1)) {
            "[Item Shop]" -> {
                player.openInventory(Bukkit.createInventory(null, 18, "Item Shop").apply {
                    setItem(1, ItemUtils.createSkull("${ChatColor.DARK_PURPLE}${ChatColor.BOLD}poti336", lore = listOf("${ChatColor.WHITE}64 Gold"), owner = "poti336"))
                    setItem(2, ItemUtils.create(Material.IRON_SWORD, lore = listOf("${ChatColor.WHITE}5 Gold")))
                    setItem(3, ItemUtils.create(Material.IRON_HELMET, lore = listOf("${ChatColor.WHITE}3 Gold")))
                    setItem(4, ItemUtils.create(Material.IRON_CHESTPLATE, lore = listOf("${ChatColor.WHITE}6 Gold")))
                    setItem(5, ItemUtils.create(Material.IRON_LEGGINGS, lore = listOf("${ChatColor.WHITE}4 Gold")))
                    setItem(6, ItemUtils.create(Material.IRON_BOOTS, lore = listOf("${ChatColor.WHITE}3 Gold")))
                    setItem(7, ItemUtils.create(Material.ENDER_PEARL, lore = listOf("${ChatColor.WHITE}32 Gold")))

                    setItem(11, ItemUtils.create(Material.CAKE, lore = listOf("${ChatColor.WHITE}1 Gold")))
                    setItem(12, ItemUtils.create(Material.BOW, lore = listOf("${ChatColor.WHITE}1 Gold")))
                    setItem(13, ItemUtils.create(Material.ARROW, lore = listOf("${ChatColor.WHITE}5 Gold"), amount = 16))
                    setItem(14, ItemUtils.create(Material.BOOK, lore = listOf("${ChatColor.WHITE}5 Gold")))
                    setItem(15, ItemUtils.create(Material.RAW_BEEF, lore = listOf("${ChatColor.WHITE}1 Gold")))
                    setItem(16, ItemUtils.create(Material.MILK_BUCKET, lore = listOf("${ChatColor.WHITE}5 Gold")))
                })
            }
            "[Potion Shop]" -> {
                player.openInventory(Bukkit.createInventory(null, 18, "Potion Shop").apply {
                    setItem(2, ItemUtils.create(Material.POTION, lore = listOf("${ChatColor.WHITE}1 Gold")))
                    setItem(3, ItemUtils.create(Material.SPECKLED_MELON, lore = listOf("${ChatColor.WHITE}2 Gold")))
                    setItem(4, ItemUtils.create(Material.FERMENTED_SPIDER_EYE, lore = listOf("${ChatColor.WHITE}3 Gold")))
                    setItem(5, ItemUtils.create(Material.GOLDEN_CARROT, lore = listOf("${ChatColor.WHITE}3 Gold")))
                    setItem(6, ItemUtils.create(Material.REDSTONE, lore = listOf("${ChatColor.WHITE}1 Gold")))

                    setItem(11, ItemUtils.create(Material.BREWING_STAND_ITEM, lore = listOf("${ChatColor.WHITE}10 Gold")))
                    setItem(12, ItemUtils.create(Material.SUGAR, lore = listOf("${ChatColor.WHITE}2 Gold")))
                    setItem(13, ItemUtils.create(Material.GHAST_TEAR, lore = listOf("${ChatColor.WHITE}15 Gold")))
                    setItem(14, ItemUtils.create(Material.MAGMA_CREAM, lore = listOf("${ChatColor.WHITE}2 Gold")))
                    if ((AnnihilationGameManager.currentGame?.currentPhase?.int ?: 0) > 3) setItem(15, ItemUtils.create(Material.BLAZE_POWDER, lore = listOf("${ChatColor.WHITE}15 Gold")))
                })
            }
        }
    }

    @EventHandler
    private fun removeSoulboundOnDrop(e: PlayerDropItemEvent) {
        val item = e.itemDrop

        if (soulboundTag in item.itemStack.itemMeta.lore) item.remove()
    }

    @EventHandler
    private fun removeSoulboundOnDeath(e: PlayerDeathEvent) {
        e.drops.removeAll { it?.itemMeta?.lore?.contains(soulboundTag) == true }
    }

    @EventHandler
    private fun giveSoulbound(e: PlayerRespawnEvent) {
        if (AnnihilationGameManager.currentGame == null) return
        e.player.giveSoulbounds()
    }

    @EventHandler
    private fun autoTeamOnJoin(e: PlayerJoinEvent) {
        val player = e.player
        val currentGame = AnnihilationGameManager.currentGame
        currentGame?.map?.teams?.map { AnnihilationPlugin.server.scoreboardManager.mainScoreboard.getTeam(it.name) }?.minBy { team -> team.entries.filter { Bukkit.getOfflinePlayer(it).isOnline }.size }?.addPlayer(player)
    }

    @EventHandler
    private fun updateScoreboard(e: PlayerJoinEvent) {
        val player = e.player
        ScoreboardUtils.update(player)
    }

    @EventHandler
    private fun onNexus(e: BlockBreakEvent) {
        val block = e.block
        val currentGame = AnnihilationGameManager.currentGame

        if (block.type != Material.ENDER_STONE || currentGame == null || currentGame.currentPhase.int < 2) return

        val player = e.player
        val team = currentGame.map.nexusLocations.toList().first { it.second == block.location }.first
        val mainScoreboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard

        if (mainScoreboard.getPlayerTeam(player).name == team.name) return

        currentGame.nexus[team] = currentGame.nexus[team]!!.dec()
        AnnihilationPlugin.server.broadcastMessage("$prefix ${mainScoreboard.getPlayerTeam(player).prefix}${player.displayName}${ChatColor.RESET} attacked ${team}Nexus (${currentGame.nexus[team]})")
        AnnihilationPlugin.server.onlinePlayers.forEach { ScoreboardUtils.update(it) }
        if (currentGame.nexus[team] == 0) {
            block.type = Material.BEDROCK
            AnnihilationPlugin.server.broadcastMessage("$prefix ${team}${mainScoreboard.getTeam(team.name).name}${ChatColor.RESET} Destroyed")
        }

        if (currentGame.nexus.filter { it.value != 0 }.size < 2) {
            AnnihilationGameManager.end()
        }
    }

    @EventHandler
    private fun modifyDeathMessage(e: PlayerDeathEvent) {
        val dead = e.entity
        val killer = e.entity.killer
        val mainboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard

        val deadColor = if (mainboard.getPlayerTeam(dead) != null) mainboard.getPlayerTeam(dead).prefix else ""

        e.droppedExp = 0

        if (killer != null) {
            val killerColor = if (mainboard.getPlayerTeam(killer) != null) mainboard.getPlayerTeam(killer).prefix else ""
            e.deathMessage = "$killerColor${killer.displayName}${ChatColor.RESET} killed $deadColor${dead.displayName}"
        } else {
            e.deathMessage = "$deadColor${dead.displayName} ${ChatColor.RESET}dead"
        }
    }

    @EventHandler
    private fun changeSpawnOnDeath(e: PlayerDeathEvent) {
        val player = e.entity
        val currentGame = AnnihilationGameManager.currentGame
        val mainboard = AnnihilationPlugin.server.scoreboardManager.mainScoreboard

        if (currentGame != null) {
            player.setBedSpawnLocation(currentGame.map.spawns[ChatColor.valueOf(mainboard.getPlayerTeam(player).name)]?.random(), true)
        } else {
            player.setBedSpawnLocation(Location(player.world, .0, 100.0, .0), true)
        }
    }

    @EventHandler
    private fun autoRespawning(e: PlayerDeathEvent) {
        val player = e.entity

        TimerAPI.build("annihilation-respawn", 1, 20) {
            end {
                player.spigot().respawn()
            }
        }
    }

    @EventHandler
    private fun openOrCreateEnderfurnace(e: PlayerInteractEvent) {
        val block = e.clickedBlock ?: return
        val player = e.player
        val furnace = enderFurnaces[player.uniqueId]
        val currentGame = AnnihilationGameManager.currentGame ?: return

        if (block.type == Material.FURNACE) {
            if (block.location !in currentGame.map.enderFurnaces) return
            e.isCancelled = true
            val inventory = enderFurnaces[player.uniqueId] ?: Bukkit.createInventory(player, InventoryType.FURNACE, "Ender Furnace")
            if (furnace == null) enderFurnaces[player.uniqueId] = inventory
            player.openInventory(inventory)
        }
    }

    @EventHandler
    private fun onAttackCombatLogger(e: PlayerDeathEvent) {
        val entity = e.entity ?: return
        if (!CitizensAPI.getNPCRegistry().isNPC(entity)) return

        entity.inventory.contents.filterNotNull().forEach {
            entity.world.dropItemNaturally(entity.location, it)
        }
    }

    @EventHandler
    private fun createCombatLogger(e: PlayerQuitEvent) {
        val player = e.player
        val logger = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.name)
        logger.spawn(player.location)

        TimerAPI.build("combatlogger-autoremove", 15, 20, 1) {
            start {
                val loggerEntity = logger.entity as Player
                logger.isFlyable = true
                logger.isProtected = false
                loggerEntity.health = player.health
                loggerEntity.allowFlight = true
                loggerEntity.isFlying = true
                loggerEntity.inventory.helmet = player.inventory.helmet
                loggerEntity.inventory.chestplate = player.inventory.chestplate
                loggerEntity.inventory.leggings = player.inventory.leggings
                loggerEntity.inventory.boots = player.inventory.boots
                loggerEntity.inventory.addItem(*player.inventory.contents.filterNotNull().toTypedArray())
            }
            end {
                logger.despawn()
            }
        }.run()
    }

    @EventHandler
    private fun removeCombatLogger(e: PlayerJoinEvent) {
        val player = e.player
        val logger = CitizensAPI.getNPCRegistry().firstOrNull { it.name == player.name }

        if (logger != null) {
            if ((logger.entity as? Player ?: return).health < 1.0) {
                player.inventory.contents = emptyArray()
                player.spigot().respawn()
            }
            CitizensAPI.getNPCRegistry().removeAll { it.id == logger.id }
        }
    }

    @EventHandler
    private fun onBreakMidBuff(e: BlockBreakEvent) {
        val currentGame = AnnihilationGameManager.currentGame
        if (e.block.type != Material.OBSIDIAN || currentGame == null) return
        if (currentGame.currentPhase.int < 4) return
    }

    @EventHandler
    private fun protectFromPlace(e: BlockPlaceEvent) {
        val currentGame = AnnihilationGameManager.currentGame
        val block = e.block
        val player = e.player
        if (
            currentGame?.map?.protectedZone?.any { block.x in it.minX..it.maxX && block.z in it.minZ..it.maxZ } == true &&
            player.gameMode != GameMode.CREATIVE
        ) {
            e.isCancelled = true
            return
        }
    }

    @EventHandler
    private fun unregisterPlacedBlock(e: BlockBreakEvent) {
        if (e.block in placedBlocks || e.player.gameMode == GameMode.CREATIVE) {
            placedBlocks -= e.block
            return
        }
    }

    @EventHandler
    private fun registerPlacedBlock(e: BlockPlaceEvent) { if (e.player.gameMode != GameMode.CREATIVE) placedBlocks += e.blockPlaced }

    @EventHandler
    private fun directlyAddItems(e: BlockBreakEvent) {
        val block = e.block
        val player = e.player
        val tool = player.itemInHand ?: return
        val currentGame = AnnihilationGameManager.currentGame

        if (block.isProtected(player)) {
            e.isCancelled = true
            return
        }

        if (
            (!block.isProperTool(tool.type) && player.gameMode != GameMode.CREATIVE)
            || (currentGame != null && block.location in currentGame.map.enderFurnaces)
        ) {
            e.isCancelled = true
            return
        }
    }

    @EventHandler
    private fun restoreOreMined(e: BlockBreakEvent) {
        val block = e.block
        val player = e.player
        val tool = player.itemInHand ?: return
        val currentGame = AnnihilationGameManager.currentGame

        if (block.isProtected(player)) {
            e.isCancelled = true
            return
        }

        if (
            (!block.isProperTool(tool.type) && player.gameMode != GameMode.CREATIVE)
            || (currentGame != null && block.location in currentGame.map.enderFurnaces)
            ) {
            e.isCancelled = true
            return
        }

        fun shouldBedrock(material: Material) = material in listOf(
            Material.IRON_ORE,
            Material.LAPIS_ORE,
            Material.COAL_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.GRAVEL
        )

        when (block.type) {
            Material.IRON_ORE,
            Material.LAPIS_ORE,
            Material.COAL_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.GRAVEL,
            Material.LOG,
            Material.MELON_BLOCK
            -> {
                if (block.type == Material.DIAMOND_ORE && (currentGame?.currentPhase?.int ?: 0) < 3) return
                e.isCancelled = true
                val origin = block.type
                val originData = block.state.data
                val originId = block.state.typeId

                if (block.type == Material.GRAVEL) {
                    player.inventory.addItem(
                        ItemStack(Material.STRING, Random.nextInt(0..2)),
                        ItemStack(Material.FLINT, Random.nextInt(0..2)),
                        ItemStack(Material.FEATHER, Random.nextInt(0..2)),
                        ItemStack(Material.ARROW, Random.nextInt(0..1))
                    )
                } else {
                    player.inventory.addItem(*block.getFortuneDrops(tool).toTypedArray()).forEach { (_, item) ->
                        player.world.dropItemNaturally(player.location, item)
                    }
                }

                player.giveExp(e.expToDrop)
                e.expToDrop = 0
                block.type = if (shouldBedrock(origin)) Material.BEDROCK else Material.AIR
                player.itemInHand = player.itemInHand.apply {
                    durability = (1 * durability.dec().dec()).toShort()
                }

                TimerAPI.build(
                    "restore-mine-${System.currentTimeMillis()}",
                    if (block.type == Material.DIAMOND_ORE || block.type == Material.EMERALD_ORE) 40 else 20,
                    20,
                    0
                ) {
                    end {
                        block.type = origin
                        block.state.typeId = originId
                        block.state.data = originData
                        block.state.update(true, true)
                    }
                }.run()
            }
            else -> return
        }
    }
}
