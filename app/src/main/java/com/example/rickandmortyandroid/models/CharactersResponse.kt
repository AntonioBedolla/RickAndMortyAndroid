package com.example.rickandmortyandroid.models

// Respuesta paginada de la API para la lista de personajes
data class CharactersResponse(
    val info: PageInfo,
    val results: List<Character>
)
