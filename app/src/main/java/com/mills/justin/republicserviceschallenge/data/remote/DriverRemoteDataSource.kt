package com.mills.justin.republicserviceschallenge.data.remote

interface DriverRemoteDataSource {
    fun fetchData(): List<RemoteData>
}