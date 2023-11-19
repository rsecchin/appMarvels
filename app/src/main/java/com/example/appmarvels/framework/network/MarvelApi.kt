package com.example.appmarvels.framework.network

import com.example.appmarvels.framework.network.response.CharacterResponse
import com.example.appmarvels.framework.network.response.ComicsResponse
import com.example.appmarvels.framework.network.response.DataWrapperResponse
import com.example.appmarvels.framework.network.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters(
        @QueryMap
        queries: Map<String, String>
    ): DataWrapperResponse<CharacterResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<ComicsResponse>

    @GET("characters/{characterId}/events")
    suspend fun getEvents(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<EventResponse>
}