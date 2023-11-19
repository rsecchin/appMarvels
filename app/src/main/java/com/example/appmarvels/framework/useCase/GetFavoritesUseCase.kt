package com.example.appmarvels.framework.useCase

import com.example.appmarvels.framework.repository.FavoritesRepository
import com.example.appmarvels.framework.useCase.base.CoroutinesDispatchers
import com.example.appmarvels.framework.model.Character
import com.example.appmarvels.framework.useCase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetFavoritesUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<List<Character>>
}

class GetFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, List<Character>>(), GetFavoritesUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<List<Character>> {
        return withContext(dispatchers.io()) {
            favoritesRepository.getAll()
        }
    }
}