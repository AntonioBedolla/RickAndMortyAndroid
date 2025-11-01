package com.example.rickandmortyandroid.models

// Estado de la UI para la lista de personajes
data class CharactersUiState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val page: Int = 1,
    val hasMore: Boolean = true,
    val error: String? = null
)
