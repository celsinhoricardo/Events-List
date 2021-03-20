package com.example.events.network.remote

import com.example.events.model.Event
import com.example.events.model.User
import com.example.events.network.EventService
import com.example.events.network.remote.response.EventListener
import com.example.events.network.remote.response.EventsListener
import com.example.events.network.remote.response.GenericListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class EventsRemoteRepository @Inject constructor(private val eventService: EventService) {

    fun listEvents(listener: EventsListener) {
        eventService.listEvents()
            .enqueue(object : Callback<List<Event>> {
                override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                    if (response.isSuccessful) {
                        listener.onSuccess(response.body())
                    } else {
                        listener.onError()
                    }
                }

                override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                    listener.onError()
                }
            })
    }

    fun getEvent(eventId: String, listener: EventListener) {
        eventService.getEvent(eventId)
            .enqueue(object : Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    if (response.isSuccessful) {
                        listener.onSuccess(response.body())
                    } else {
                        listener.onError()
                    }
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    listener.onError()
                }
            })
    }

    fun checkIn(user: User, listener: GenericListener) {
        eventService.checkIn(user)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() in 200..299) {
                        listener.onSuccess()
                    } else {
                        listener.onError()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    listener.onError()
                }
            })
    }
}