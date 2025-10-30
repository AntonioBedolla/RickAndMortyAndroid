package com.example.rickandmortyandroid.models

import com.squareup.moshi.Json

// Representa un personaje tal y como viene en la API
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationShort,
    val location: LocationShort,
    @Json(name = "image") val imageUrl: String,
    val episode: List<String>
)
