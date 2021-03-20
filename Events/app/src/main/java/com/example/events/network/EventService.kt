package com.example.events.network

import com.example.events.BuildConfig
import com.example.events.model.Event
import com.example.events.model.User
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {

    // List All Events
    @GET("events/")
    fun listEvents(): Call<List<Event>>

    @GET("events/{id}")
    fun getEvent(@Path("id") id: String): Call<Event>

    @POST("checkin")
    fun checkIn(@Body user: User): Call<Void> // sem informaçao de retorno por falha na chamada


    companion object Factory {
        fun create(): EventService {
            val retrofit = retrofit2.Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()

            return retrofit.create(EventService::class.java)
        }
    }
}