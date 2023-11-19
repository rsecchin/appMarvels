package com.example.appmarvels.framework.remote

import com.example.appmarvels.framework.model.CharacterPaging
import com.example.appmarvels.framework.model.Comic
import com.example.appmarvels.framework.model.Event
import com.example.appmarvels.framework.network.MarvelApi
import com.example.appmarvels.framework.network.response.toCharacterModel
import com.example.appmarvels.framework.network.response.toComicModel
import com.example.appmarvels.framework.network.response.toEventModel
import com.example.appmarvels.framework.repository.CharactersRemoteDataSource
import javax.inject.Inject

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource {

    override suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterModel()
        }
        return CharacterPaging(
            data.offset,
            data.total,
            characters
        )
    }

    override suspend fun fetchComics(characterId: Int): List<Comic> {
        return marvelApi.getComics(characterId).data.results.map {
            it.toComicModel()
        }
    }

    override suspend fun fetchEvents(characterId: Int): List<Event> {
        return marvelApi.getEvents(characterId).data.results.map {
            it.toEventModel()
        }
    }
}
