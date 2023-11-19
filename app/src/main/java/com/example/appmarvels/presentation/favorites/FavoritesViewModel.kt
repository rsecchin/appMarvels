package com.example.appmarvels.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmarvels.framework.useCase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
//    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getFavorites() = viewModelScope.launch {
        getFavoritesUseCase.invoke()
            .catch {
                UiState.ShowEmpty
            }
            .collect {
                val items = it.map { character ->
                    FavoriteItem(character.id, character.name, character.imageUrl)
                }

                _uiState.value = if (items.isEmpty()) {
                    UiState.ShowEmpty
                } else UiState.ShowFavorites(items)
            }
    }

//    private val action = MutableLiveData<Action>()
//    val state: LiveData<UiState> = action.switchMap { action ->
//        liveData(coroutinesDispatchers.main()) {
//            when (action) {
//                is Action.GetAll -> {
//                    getFavoritesUseCase.invoke()
//                        .catch {
//                            UiState.ShowEmpty
//                        }
//                        .collect {
//                            val items = it.map { character ->
//                                FavoriteItem(character.id, character.name, character.imageUrl)
//                            }
//
//                            val uiState = if (items.isEmpty()) {
//                                UiState.ShowEmpty
//                            } else UiState.ShowFavorites(items)
//                            emit(uiState)
//                        }
//                }
//            }
//        }
//    }

//    fun getAll() {
//        action.value = Action.GetAll
//    }

    sealed class UiState {
        data class ShowFavorites(val favorites: List<FavoriteItem>) : UiState()
        object ShowEmpty : UiState()
    }

//    sealed class Action {
//        object GetAll : Action()
//    }
}