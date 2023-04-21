package com.mills.justin.republicserviceschallenge.data

import kotlinx.coroutines.flow.Flow

interface DriverRepo {
    fun refreshData()
    fun drivers(): Flow<List<Driver>>
    fun routesForDriver(id: String): Flow<List<Route>>
}