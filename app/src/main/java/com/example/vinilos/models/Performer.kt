package com.example.vinilos.models
import kotlinx.serialization.Serializable


@Serializable
data class Performer (
    val performerId:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String
    )

