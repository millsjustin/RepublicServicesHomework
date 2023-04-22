package com.mills.justin.republicserviceschallenge.data

import android.util.Log
import com.mills.justin.republicserviceschallenge.data.local.DriverLocalDataSource
import com.mills.justin.republicserviceschallenge.data.local.LocalData
import com.mills.justin.republicserviceschallenge.data.local.LocalDriver
import com.mills.justin.republicserviceschallenge.data.local.LocalRoute
import com.mills.justin.republicserviceschallenge.data.remote.ApiResult
import com.mills.justin.republicserviceschallenge.data.remote.DriverRemoteDataSource
import com.mills.justin.republicserviceschallenge.data.remote.RemoteData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DriverRepoImpl @Inject constructor(
    private val remoteDataSource: DriverRemoteDataSource,
    private val localDataSource: DriverLocalDataSource,
    private val externalScope: CoroutineScope,
) : DriverRepo {

    override fun refreshData() {
        externalScope.launch {
            when (val result = remoteDataSource.fetchData()) {
                is ApiResult.Failure -> {
                    // Just logging the error in a production app we would want to propagate the
                    //  error to a user visible message
                    Log.w(
                        DriverRepoImpl::class.java.simpleName,
                        "failed to fetch data from api",
                        result.error
                    )
                }

                is ApiResult.Success -> {
                    localDataSource.insert(result.value.toLocalData())
                }
            }
        }
    }

    override fun drivers(): Flow<List<Driver>> {
        return localDataSource.drivers()
            .map { it.map(this::localDriverToDriver) }
            .flowOn(Dispatchers.IO)
    }

    override fun routesForDriver(driverId: String): Flow<List<Route>> {
        return localDataSource.routes()
            .map { it.map(this::localRouteToRoute) }
            .map { routes ->
                val driverIdNum = driverId.toInt()

                // business logic
                //  If the driver id is the same as the route id then display the route.
                //  If the driver id is divisible by 2 then show the first R type route.
                //  If the driver id is divisible by 5 then display the second C type route.
                //  If the driver id doesn’t meet any of the conditions above, then show the last I type route.

                val idsAreTheSame = routes.firstOrNull { it.id == driverId }

                val divisibleBy2 = if (driverIdNum % 2 == 0) {
                    routes.firstOrNull { it.type == "R" }
                } else null

                val divisibleBy5 = if (driverIdNum % 5 == 0) {
                    routes.asSequence()
                        .filter { it.type == "C" }
                        .take(2)
                        .drop(1)
                        .firstOrNull()
                } else null

                val conditionsNotMeet = if (idsAreTheSame == null &&
                    divisibleBy2 == null &&
                    divisibleBy5 == null
                ) {
                    routes.lastOrNull { it.type == "I" }
                } else null

                listOfNotNull(idsAreTheSame, divisibleBy2, divisibleBy5, conditionsNotMeet)
            }
            .flowOn(Dispatchers.IO)
    }

    private fun RemoteData.toLocalData(): LocalData {
        return LocalData(
            drivers = drivers.mapIndexed { index, remoteDriver ->
                LocalDriver(
                    id = remoteDriver.id,
                    name = remoteDriver.name,
                    index = index,
                )
            },
            routes = routes.mapIndexed { index, remoteRoute ->
                LocalRoute(
                    id = remoteRoute.id,
                    name = remoteRoute.name,
                    type = remoteRoute.type,
                    index = index,
                )
            })
    }

    private fun localDriverToDriver(localDriver: LocalDriver): Driver {
        return Driver(
            id = localDriver.id,
            name = localDriver.name,
        )
    }

    private fun localRouteToRoute(localRoute: LocalRoute): Route {
        return Route(
            id = localRoute.id,
            name = localRoute.name,
            type = localRoute.type,
        )
    }
}