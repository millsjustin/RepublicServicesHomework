package com.mills.justin.republicserviceschallenge.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRoute(
    val id: String,
    val type: String,
    val name: String,
)