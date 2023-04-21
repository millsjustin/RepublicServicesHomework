package com.mills.justin.republicserviceschallenge.data.local

import kotlinx.coroutines.flow.Flow

interface DriverLocalDataSource {
    fun insert(data: LocalData)
    fun drivers(): Flow<List<LocalDriver>>
}