package com.example.appmarvels.framework.useCase

import com.example.appmarvels.framework.repository.FavoritesRepository
import com.example.appmarvels.framework.useCase.base.CoroutinesDispatchers
import com.example.appmarvels.framework.useCase.base.ResultStatus
import com.example.appmarvels.framework.useCase.base.UseCase
import com.example.appmarvels.framework.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddFavoritesUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val characterId: Int, val name: String, val imageUrl: String)
}

class AddFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<AddFavoritesUseCase.Params, Unit>(), AddFavoritesUseCase {

    override suspend fun doWork(params: AddFavoritesUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            favoritesRepository.saveFavorite(
                Character(params.characterId, params.name, params.imageUrl)
            )
            ResultStatus.Success(Unit)
        }
    }
}