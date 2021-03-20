package com.example.events.network.remote.response

import com.example.events.model.Event

interface EventsListener {
    fun onSuccess(events: List<Event>?)
    fun onError()

}