package com.mills.justin.republicserviceschallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface DriverDao {

    @Insert
    fun insertAllDrivers(vararg drivers: EntityDriver)

    @Insert
    fun insertAllRoutes(vararg routes: EntityRoute)

    @Query("DELETE FROM driver WHERE id NOT IN (:ids)")
    fun deleteDriversNotIn(ids: List<String>)

    @Query("DELETE FROM route WHERE id NOT IN (:ids)")
    fun deleteRoutesNotIn(ids: List<String>)

    @Transaction
    fun saveData(data: LocalData) {
        deleteDriversNotIn(data.drivers.map { it.id })
        deleteRoutesNotIn(data.routes.map { it.id })
        val driverEntities = data.drivers.map { EntityDriver.fromLocal(it) }.toTypedArray()
        insertAllDrivers(*driverEntities)
        val routeEntities = data.routes.map { EntityRoute.fromLocal(it) }.toTypedArray()
        insertAllRoutes(*routeEntities)
    }

    @Query("SELECT * from driver ORDER BY `index` ASC")
    fun drivers(): Flow<List<EntityDriver>>

    @Query("SELECT * from route ORDER BY `index` ASC")
    fun routes(): Flow<List<EntityRoute>>
}