package com.example.rickandmortyandroid.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyandroid.models.CharactersUiState
import com.example.rickandmortyandroid.repositories.CharacterRepository
import com.example.rickandmortyandroid.utils.DependencyContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(private val repository: CharacterRepository = DependencyContainer.characterRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState


    // Cargar la primera página o refrescar
    fun loadFirstPage(name: String? = null, status: String? = null, species: String? = null) {
        _uiState.value = CharactersUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val response = repository.getCharacters(page = 1, name = name, status = status, species = species)
                _uiState.value = CharactersUiState(
                    isLoading = false,
                    characters = response.results,
                    page = 1,
                    hasMore = response.info.next != null
                )
            } catch (e: Exception) {
                _uiState.value = CharactersUiState(isLoading = false, error = e.message ?: "Error desconocido")
            }
        }
    }


    // Cargar la siguiente página (paginación)
    fun loadNextPage() {
        val current = _uiState.value
        if (current.isLoading || !current.hasMore) return


        val nextPage = current.page + 1
        _uiState.value = current.copy(isLoading = true)


        viewModelScope.launch {
            try {
                val response = repository.getCharacters(page = nextPage)
                val combined = current.characters + response.results
                _uiState.value = current.copy(
                    isLoading = false,
                    characters = combined,
                    page = nextPage,
                    hasMore = response.info.next != null
                )
            } catch (e: Exception) {
                _uiState.value = current.copy(isLoading = false, error = e.message ?: "Error desconocido")
            }
        }
    }

}