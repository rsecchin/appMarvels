package com.example.appmarvels.framework

import androidx.paging.PagingSource
import com.example.appmarvels.framework.model.Comic
import com.example.appmarvels.framework.model.Event
import com.example.appmarvels.framework.paging.CharactersPagingSource
import com.example.appmarvels.framework.repository.CharacterRepository
import com.example.appmarvels.framework.repository.CharactersRemoteDataSource
import com.example.appmarvels.framework.model.Character
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor (
    private val remoteDataSource: CharactersRemoteDataSource
) : CharacterRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource, query)
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }
}