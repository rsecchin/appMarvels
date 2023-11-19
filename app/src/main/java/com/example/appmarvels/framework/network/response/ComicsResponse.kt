package com.example.appmarvels.framework.network.response

import com.example.appmarvels.framework.model.Comic
import com.google.gson.annotations.SerializedName

data class ComicsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun ComicsResponse.toComicModel(): Comic {
    return Comic (
        id = this.id,
        imageUrl = this.thumbnail.getHttpsUrl()
    )
}