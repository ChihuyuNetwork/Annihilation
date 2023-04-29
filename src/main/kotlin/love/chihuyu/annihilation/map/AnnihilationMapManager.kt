package love.chihuyu.annihilation.map

import love.chihuyu.annihilation.AnnihilationPlugin.Companion.AnnihilationMapConfig

object AnnihilationMapManager {

    val loadedMaps = mutableMapOf<String, AnnihilationMap>()

    fun load() {
        (AnnihilationMapConfig.getList("maps") as List<AnnihilationMap>).forEach {
            loadedMaps[it.id] = it
        }
    }
}
