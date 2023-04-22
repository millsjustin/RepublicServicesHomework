package com.mills.justin.republicserviceschallenge.data.local

import kotlinx.coroutines.flow.Flow

interface DriverLocalDataSource {
    suspend fun insert(data: LocalData)
    fun drivers(): Flow<List<LocalDriver>>
    fun routes(): Flow<List<LocalRoute>>
}