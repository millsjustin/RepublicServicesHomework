package com.mills.justin.republicserviceschallenge.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DriverLocalDataSourceImpl @Inject constructor(
    private val driverDatabase: DriverDatabase,
) : DriverLocalDataSource {

    override suspend fun insert(data: LocalData) {
        withContext(Dispatchers.IO) {
            driverDatabase.driverDao().saveData(data)
        }
    }

    override fun drivers(): Flow<List<LocalDriver>> {
        return driverDatabase.driverDao().drivers()
            .map { drivers ->
                drivers.map { it.toLocal() }
            }
            .flowOn(Dispatchers.IO)
    }

    override fun routes(): Flow<List<LocalRoute>> {
        return driverDatabase.driverDao().routes()
            .map { routes ->
                routes.map { it.toLocal() }
            }
            .flowOn(Dispatchers.IO)
    }
}