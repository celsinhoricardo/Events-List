package com.example.events.network.remote.response

import com.example.events.model.Event

interface EventListener {
    fun onSuccess(event: Event?)
    fun onError()

}