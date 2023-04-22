package com.mills.justin.republicserviceschallenge.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteDriver(
    val id: String,
    val name: String,
)