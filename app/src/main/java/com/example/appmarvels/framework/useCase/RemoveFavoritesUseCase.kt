package com.example.appmarvels.framework.useCase

import com.example.appmarvels.framework.repository.FavoritesRepository
import com.example.appmarvels.framework.useCase.base.CoroutinesDispatchers
import com.example.appmarvels.framework.useCase.base.ResultStatus
import com.example.appmarvels.framework.useCase.base.UseCase
import com.example.appmarvels.framework.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoveFavoritesUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val characterId: Int, val name: String, val imageUrl: String)
}

class RemoveFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<RemoveFavoritesUseCase.Params, Unit>(), RemoveFavoritesUseCase {

    override suspend fun doWork(params: RemoveFavoritesUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            favoritesRepository.deleteFavorite(
                Character(params.characterId, params.name, params.imageUrl)
            )
            ResultStatus.Success(Unit)
        }
    }
}