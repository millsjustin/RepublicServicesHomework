package com.mills.justin.republicserviceschallenge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route")
data class EntityRoute(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val index: Int,
) {

    fun toLocal(): LocalRoute {
        return LocalRoute(
            id = id,
            name = name,
            type = type,
            index = index,
        )
    }

    companion object {
        fun fromLocal(localRoute: LocalRoute): EntityRoute {
            return EntityRoute(
                id = localRoute.id,
                name = localRoute.name,
                type = localRoute.type,
                index = localRoute.index,
            )
        }
    }
}