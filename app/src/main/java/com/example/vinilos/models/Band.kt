package com.example.vinilos.models

import kotlinx.serialization.Serializable

@Serializable
data class Band (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String,
    val albums: List<Album>
    )