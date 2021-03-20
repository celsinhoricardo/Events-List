package com.example.events.model

data class Event(
    val date: String?,
    var image: String,
    var price: String?,
    var latitude: String?,
    var description: String?,
    var id: String?,
    var title: String?,
    var people: Array<String>?,
    var longitude: String?
)