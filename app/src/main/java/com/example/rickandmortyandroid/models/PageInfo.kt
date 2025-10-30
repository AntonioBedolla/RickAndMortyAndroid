package com.example.rickandmortyandroid.models

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
