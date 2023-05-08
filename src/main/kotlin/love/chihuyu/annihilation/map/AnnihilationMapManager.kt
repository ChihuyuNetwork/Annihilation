package love.chihuyu.annihilation.map

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationMapConfig
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationPlugin
import love.chihuyu.annihilation.AnnihilationPlugin.Companion.mapFile


object AnnihilationMapManager {

    val cachedMaps = mutableMapOf<String, AnnihilationMap>()

    fun cache() {
        (AnnihilationMapConfig.getList("maps") as? List<AnnihilationMap>)?.forEach {
            AnnihilationPlugin.logger.info(it.toString())
            cachedMaps[it.id] = it
        }
    }

    fun save() {
        AnnihilationMapConfig.set("maps", cachedMaps.values.toList())
        AnnihilationMapConfig.save(mapFile)
    }
}
