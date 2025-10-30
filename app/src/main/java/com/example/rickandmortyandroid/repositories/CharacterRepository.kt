package com.example.rickandmortyandroid.repositories
import com.example.rickandmortyandroid.models.Character
import com.example.rickandmortyandroid.models.CharactersResponse
import com.example.rickandmortyandroid.interfaces.ApiService

// Repositorio que la capa UI (ViewModel) consumirá.
// Aquí se pueden añadir caches, transformaciones y manejo de errores.
class CharacterRepository(private val apiService: ApiService) {

    // Obtiene personajes con parámetros de búsqueda y paginación
    suspend fun getCharacters(page: Int = 1, name: String? = null, status: String? = null, species: String? = null): CharactersResponse {
        return apiService.getCharacters(page = page, name = name, status = status,  species = species)
    }

    // Obtener personaje por id
    suspend fun getCharacterById(id: Int): Character {
        return apiService.getCharacterById(id).results
    }
}