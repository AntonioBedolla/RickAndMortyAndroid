package com.example.rickandmortyandroid.interfaces
import com.example.rickandmortyandroid.models.CharacterResponseHolder
import com.example.rickandmortyandroid.models.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import  retrofit2.http.Query

// Interfaz que usaremos tanto para la implementación real (Retrofit)
// como para el Mock (simulación), así el repositorio no cambia.
interface ApiService {
    @GET("/api/character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null
    ): CharactersResponse

    @GET("/api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterResponseHolder
}