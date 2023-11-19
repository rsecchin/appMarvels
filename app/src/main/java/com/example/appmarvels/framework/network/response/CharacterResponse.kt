package com.example.appmarvels.framework.network.response

import com.example.appmarvels.framework.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel(): Character{
    return Character (
        id = this.id,
        name = this.name,
        imageUrl = this.thumbnail.getHttpsUrl()
    )
}
