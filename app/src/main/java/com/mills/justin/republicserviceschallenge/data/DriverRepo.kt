package com.mills.justin.republicserviceschallenge.data

import kotlinx.coroutines.flow.Flow

interface DriverRepo {
    fun refreshData()
    fun drivers(): Flow<List<Driver>>
    fun routesForDriver(driverId: String): Flow<List<Route>>
}