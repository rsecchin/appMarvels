package com.example.appmarvels.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.appmarvels.databinding.FragmentCharacterDetailBinding
import com.example.appmarvels.framework.imageLoader.ImageLoader
import com.example.appmarvels.presentation.extension.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding: FragmentCharacterDetailBinding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<CharacterDetailFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCharacterDetailBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    @Suppress("MagicNumber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArg = args.detailViewArg
        binding.imageCharacter.run {
            transitionName = detailViewArg.name
            imageLoader.load(this, detailViewArg.imageUrl)
        }

        setSharedElementTransitionOnEnter()

        loadCategoriesAndObserveUiState(detailViewArg)
        setAndObserveFavoriteUiState(detailViewArg)
    }

    private fun loadCategoriesAndObserveUiState(detailViewArg: DetailViewArg) {
        viewModel.categories.load(detailViewArg.characterId)
        viewModel.categories.state.observe(viewLifecycleOwner) { uiState ->

            binding.flipperDetail.displayedChild = when (uiState) {

                UiActionStateLiveData.UiState.Loading -> FLIPPER_CHILD_POSITION_LOADING
                is UiActionStateLiveData.UiState.Success -> {
                    binding.recyclerParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                    }

                    FLIPPER_CHILD_POSITION_SUCCESS
                }
                UiActionStateLiveData.UiState.Error -> {

                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.categories.load(detailViewArg.characterId)
                    }

                    FLIPPER_CHILD_POSITION_ERROR
                }
                UiActionStateLiveData.UiState.Empty -> FLIPPER_CHILD_POSITION_EMPTY
            }
        }
    }

    private fun setAndObserveFavoriteUiState(detailViewArg: DetailViewArg) {

        viewModel.favorite.run {
            checkFavorite(detailViewArg.characterId)

            binding.imageFavoriteIcon.setOnClickListener {
                update(detailViewArg)
            }

            state.observe(viewLifecycleOwner) { uiState ->
                binding.flipperFavorite.displayedChild = when (uiState) {
                    FavoriteUiActionStateLiveData.UiState.Loading -> FLIPPER_FAVORITE_CHILD_POSITION_LOADING

                    is FavoriteUiActionStateLiveData.UiState.Icon -> {
                        binding.imageFavoriteIcon.setImageResource(uiState.icon)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                    is FavoriteUiActionStateLiveData.UiState.Error -> {
                        showShortToast(uiState.messageResId)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                }
            }
        }
    }

    // Define a animação da transição como "move"
    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
                sharedElementEnterTransition = this
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_SUCCESS = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3
        private const val FLIPPER_FAVORITE_CHILD_POSITION_IMAGE = 0
        private const val FLIPPER_FAVORITE_CHILD_POSITION_LOADING = 1
    }
}