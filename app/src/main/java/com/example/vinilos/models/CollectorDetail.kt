package com.example.vinilos.models
import kotlinx.serialization.Serializable


@Serializable
data class CollectorDetail (

    val collectorId:Int,
    val name:String,
    val telephone:String,
    val email:String ,
    val comments: List<Comment>,
    val favoritePerformers: List<Performer>

    )