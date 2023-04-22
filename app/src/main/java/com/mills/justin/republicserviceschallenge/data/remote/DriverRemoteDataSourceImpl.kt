package com.mills.justin.republicserviceschallenge.data.remote

import javax.inject.Inject

class DriverRemoteDataSourceImpl @Inject constructor(

) : DriverRemoteDataSource {
    override fun fetchData(): RemoteData {
        return RemoteData(
            drivers = listOf(RemoteDriver("1", "Justin Mills")),
            routes = listOf(RemoteRoute("1", "T", "Fake Route")),
        )
    }
}