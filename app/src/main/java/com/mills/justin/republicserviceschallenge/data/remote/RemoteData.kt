package com.mills.justin.republicserviceschallenge.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteData(
    val drivers: List<RemoteDriver>,
    val routes: List<RemoteRoute>,
)