package com.mills.justin.republicserviceschallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mills.justin.republicserviceschallenge.data.Driver
import com.mills.justin.republicserviceschallenge.data.DriverRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DriverListViewModel @Inject constructor(
    private val driverRepo: DriverRepo,
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean,
        val isSorted: Boolean,
        val drivers: List<Driver>,
    )

    init {
        driverRepo.refreshData()
    }

    private val initialState
        get() = UiState(
            isLoading = true,
            isSorted = false,
            drivers = emptyList(),
        )

    private val _isSorted: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val uiState: StateFlow<UiState> = combine(
        _isSorted,
        driverRepo.drivers(),
    ) { isSorted, drivers ->
        UiState(
            isLoading = drivers.isEmpty(),
            isSorted = isSorted,
            drivers = if (isSorted) sortedDrivers(drivers) else drivers,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = initialState,
    )

    fun onClickSort() {
        _isSorted.value = true
    }


    private fun sortedDrivers(drivers: List<Driver>): List<Driver> {
        return drivers.sortedBy {
            // this assumes String Driver.name is always '<first> <last>' that doesn't work in the
            //  real world but should be good enough for this demo.
            it.name.substringAfter(' ')
        }
    }

}