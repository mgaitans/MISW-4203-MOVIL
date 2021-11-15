package com.example.vinilos.models

data class CollectorAlbums (
    val id:Int,
    val price:String,
    val status:String,
    val album:List<Album>,
    val collector: List<Collector>
)
