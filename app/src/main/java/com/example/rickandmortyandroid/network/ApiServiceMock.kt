package com.example.rickandmortyandroid.network
import com.example.rickandmortyandroid.interfaces.ApiService
import com.example.rickandmortyandroid.models.*
import kotlinx.coroutines.delay

// Implementación sencilla para devolver datos controlados (delay simula tiempo de red)
class ApiServiceMock: ApiService {
    // Datos de ejemplo
    private val sampleCharacters = (1..20).map { id ->
        Character(
            id = id,
            name = "Rick Sanchez #$id",
            status = if (id % 2 == 0) "Alive" else "Unknown",
            species = "Human",
            type = "",
            gender = if (id % 2 == 0) "Male" else "Unknown",
            origin = LocationShort("Earth (C-137)", ""),
            location = LocationShort("Citadel of Ricks", ""),
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/${id}.jpeg",
            episode = listOf("S01E01", "S01E02")
        )
    }

    override suspend fun getCharacters(page: Int, name: String?, status: String?, species: String?): CharactersResponse {
// Simular latencia
        delay(500)


// Filtrar por nombre / status / especie si se proporcionan
        val filtered = sampleCharacters.filter { c ->
            val matchName = name.isNullOrBlank() || c.name.contains(name, ignoreCase = true)
            val matchStatus = status.isNullOrBlank() || c.status.equals(status, ignoreCase = true)
            val matchSpecies = species.isNullOrBlank() || c.species.equals(species, ignoreCase = true)
            matchName && matchStatus && matchSpecies
        }


// Simular paginación: 10 por página
        val pageSize = 10
        val from = (page - 1) * pageSize
        val to = kotlin.math.min(from + pageSize, filtered.size)
        val pageResults = if (from >= filtered.size) emptyList() else filtered.subList(from, to)
        val next = if (to < filtered.size) "page=${page + 1}" else null


        return CharactersResponse(
            info = PageInfo(count = filtered.size, pages = (filtered.size + pageSize - 1) / pageSize, next = next, prev = if (page > 1) "page=${page - 1}" else null),
            results = pageResults
        )
    }


    override suspend fun getCharacterById(id: Int): CharacterResponseHolder {
        delay(300)
        val found = sampleCharacters.firstOrNull { it.id == id } ?: sampleCharacters.first()
        return CharacterResponseHolder(found)
    }
}