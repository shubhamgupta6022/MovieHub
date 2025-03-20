package com.sgupta.composite.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.mapper.MovieItemDomainModelMapper
import com.sgupta.core.ViewState
import com.sgupta.core.delegator.DelegateAdapterItem
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetMoviesQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val getMoviesQueryUseCase: GetMoviesQueryUseCase,
    private val movieItemDomainModelMapper: MovieItemDomainModelMapper
) : ViewModel() {

    private val _viewState = MutableStateFlow<SearchViewState>(SearchViewState.Loading)
    val viewState: StateFlow<SearchViewState> = _viewState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        getMovieQuery(query)
                    } else {
                        _viewState.value = SearchViewState.NoData
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun getMovieQuery(query: String) {
        getMoviesQueryUseCase.execute(GetMoviesQueryUseCase.Param(query))
            .onEach { resource ->
                _viewState.value = when (resource) {
                    is Resource.Loading -> SearchViewState.Loading
                    is Resource.Error -> SearchViewState.Error(resource.error.message ?: "Something went wrong")
                    is Resource.Success -> {
                        val items = resource.data?.movieItemResponses.orEmpty().map { movie ->
                            movieItemDomainModelMapper.convert(movie)
                        }
                        SearchViewState.Success(items)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}


sealed class SearchViewState : ViewState {
    data object Loading : SearchViewState()
    data object NoData : SearchViewState()
    data class Success(
        val items: List<DelegateAdapterItem>,
    ) : SearchViewState()

    data class Error(val error: String) : SearchViewState()
}