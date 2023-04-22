package com.mills.justin.republicserviceschallenge.data.remote

import android.content.Context
import com.mills.justin.republicserviceschallenge.R
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import okio.buffer
import okio.source
import javax.inject.Inject

class DriverRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val moshi: Moshi,
) : DriverRemoteDataSource {

    override fun fetchData(): RemoteData {

        val data = appContext.resources.openRawResource(R.raw.data).use { inputStream ->
            inputStream.source().buffer().use { source ->
                moshi.adapter(RemoteData::class.java)
                    .fromJson(source)
            }
        }

        return data!! // TODO handle null here
    }
}