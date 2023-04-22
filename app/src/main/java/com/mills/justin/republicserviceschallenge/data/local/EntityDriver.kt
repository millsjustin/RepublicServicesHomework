package com.mills.justin.republicserviceschallenge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "driver")
data class EntityDriver(
    @PrimaryKey val id: String,
    val name: String,
    val index: Int,
) {

    fun toLocal(): LocalDriver {
        return LocalDriver(
            id = id,
            name = name,
            index = index,
        )
    }

    companion object {
        fun fromLocal(localDriver: LocalDriver): EntityDriver {
            return EntityDriver(
                id = localDriver.id,
                name = localDriver.name,
                index = localDriver.index,
            )
        }
    }
}