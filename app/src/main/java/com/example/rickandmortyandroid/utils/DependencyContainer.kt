package com.example.rickandmortyandroid.utils
import com.example.rickandmortyandroid.interfaces.ApiService
import com.example.rickandmortyandroid.network.ApiServiceMock
import com.example.rickandmortyandroid.network.RetrofitProvider
import com.example.rickandmortyandroid.repositories.CharacterRepository

// Contenedor simple (sin Hilt ni Dagger). Cambia useMock a true durante desarrollo/pruebas.
object DependencyContainer {
    // Flag para alternar entre implementación real y mock
    // En producción debería venir de una configuración o BuildConfig.DEBUG
    var useMock: Boolean = true


    // Lazy inicialización de la apiService según useMock
    val apiService: ApiService by lazy {
        if (useMock) ApiServiceMock() else RetrofitProvider.create()
    }


    // Repositorios
    val characterRepository: CharacterRepository by lazy {
        CharacterRepository(apiService)
    }
}