package com.mills.justin.republicserviceschallenge.data.remote

import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

const val DATA_URL = "https://d49c3a78-a4f2-437d-bf72-569334dea17c.mock.pstmn.io/data"

class DriverRemoteDataSourceImpl @Inject constructor(
    private val moshi: Moshi,
    private val okHttpClient: OkHttpClient,
) : DriverRemoteDataSource {

    override suspend fun fetchData(): ApiResult<RemoteData> {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(DATA_URL)
                .build()
            val response = okHttpClient.newCall(request)
                .execute()

            if (!response.isSuccessful) {
                return@withContext ApiResult.Failure(
                    RuntimeException(response.toString())
                )
            }

            val data = response.body?.use { body ->
                body.source().use { source ->
                    moshi.adapter(RemoteData::class.java)
                        .fromJson(source)
                }
            } ?: return@withContext ApiResult.Failure(
                RuntimeException("failed to parse json from response: $response")
            )

            return@withContext ApiResult.Success(data)
        }
    }
}