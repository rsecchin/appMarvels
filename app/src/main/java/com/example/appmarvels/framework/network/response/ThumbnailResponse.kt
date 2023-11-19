package com.example.appmarvels.framework.network.response

import com.google.gson.annotations.SerializedName

data class ThumbnailResponse(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)

fun ThumbnailResponse.getHttpsUrl () = "$path.$extension".replace("http", "https")
