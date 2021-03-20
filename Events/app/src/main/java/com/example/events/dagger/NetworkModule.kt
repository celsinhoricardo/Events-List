package com.example.event.dagger

import com.example.events.network.EventService
import com.example.events.network.remote.EventsRemoteRepository
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideEventService(): EventService = EventService.create()

    @Provides
    fun provideEventRemoteRepository(eventService: EventService): EventsRemoteRepository =
        EventsRemoteRepository(eventService)

}