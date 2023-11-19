package com.example.appmarvels.framework.repository

import androidx.paging.PagingSource
import com.example.appmarvels.framework.model.Character
import com.example.appmarvels.framework.model.Comic
import com.example.appmarvels.framework.model.Event

interface CharacterRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

    suspend fun getComics(characterId: Int): List<Comic>

    suspend fun getEvents(characterId: Int): List<Event>
}