package com.example.vinilos.models
import kotlinx.serialization.Serializable


@Serializable
data class CollectorAlbums (
    val id:Int,
    val price:Double,
    val status:String,
    val album : Album,
    val collector : Collector,
)
