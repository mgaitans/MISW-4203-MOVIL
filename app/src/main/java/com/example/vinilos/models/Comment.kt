package com.example.vinilos.models
import kotlinx.serialization.Serializable


@Serializable
data class Comment (
    val description:String,
    val rating:String,
    val commentId:Int
)