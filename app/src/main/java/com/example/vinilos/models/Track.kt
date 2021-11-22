package com.example.vinilos.models
import kotlinx.serialization.Serializable


@Serializable
data class Track (
    val trackId:Int,
    val name:String,
    val duration:String

    )