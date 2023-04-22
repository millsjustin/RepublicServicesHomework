package com.mills.justin.republicserviceschallenge.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mills.justin.republicserviceschallenge.data.DriverRepo
import com.mills.justin.republicserviceschallenge.data.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RouteListViewModel @Inject constructor(
    private val driverRepo: DriverRepo,
    private val state: SavedStateHandle,
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean,
        val routes: List<Route>,
    )

    private val initialState: UiState
        get() = UiState(
            isLoading = true,
            routes = emptyList(),
        )

    private val args: RouteListFragmentArgs = RouteListFragmentArgs.fromSavedStateHandle(state)

    val uiState: StateFlow<UiState> = driverRepo.routesForDriver(args.id).map { routes ->
        UiState(
            isLoading = false,
            routes = routes,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = initialState,
    )
}