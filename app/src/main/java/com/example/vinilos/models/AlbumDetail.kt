package com.example.vinilos.models

data class AlbumDetail (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val tracks : List<Track>,
    val performers : List<Performer>,
    val comments : List<Comment>
)
