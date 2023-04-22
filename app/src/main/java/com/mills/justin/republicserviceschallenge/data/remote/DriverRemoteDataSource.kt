package com.mills.justin.republicserviceschallenge.data.remote

interface DriverRemoteDataSource {
    suspend fun fetchData(): ApiResult<RemoteData>
}

sealed class ApiResult<T> {
    data class Success<T>(
        val value: T,
    ) : ApiResult<T>()

    data class Failure<T>(
        val error: Throwable,
    ) : ApiResult<T>()
}