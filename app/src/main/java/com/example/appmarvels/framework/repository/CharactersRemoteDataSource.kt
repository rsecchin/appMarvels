package com.example.appmarvels.framework.repository

import com.example.appmarvels.framework.model.CharacterPaging
import com.example.appmarvels.framework.model.Comic
import com.example.appmarvels.framework.model.Event

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging

    suspend fun fetchComics(characterId: Int): List<Comic>

    suspend fun fetchEvents(characterId: Int): List<Event>
}