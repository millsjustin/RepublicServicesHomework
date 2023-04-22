package com.mills.justin.republicserviceschallenge.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DriverLocalDataSourceImpl @Inject constructor(

): DriverLocalDataSource {

    private val _data: MutableStateFlow<LocalData> = MutableStateFlow(
        LocalData(emptyList(), emptyList())
    )

    override fun insert(data: LocalData) {
        _data.value = data
    }

    override fun drivers(): Flow<List<LocalDriver>> {
        return _data.map { it.drivers }
    }

    override fun routes(): Flow<List<LocalRoute>> {
        return _data.map { it.routes }
    }
}