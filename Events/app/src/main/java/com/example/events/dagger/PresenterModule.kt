package com.example.event.dagger


import com.example.events.network.remote.EventsRemoteRepository
import com.example.events.ui.event.checkIn.CheckInContract
import com.example.events.ui.event.checkIn.CheckInPresenter
import com.example.events.ui.event.detail.EventDetailContract
import com.example.events.ui.event.detail.EventDetailPresenter
import com.example.events.ui.event.list.EventListContract
import com.example.events.ui.event.list.EventListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideEvenListPresenter(
        eventsRemoteRepository: EventsRemoteRepository
    ): EventListContract.Presenter = EventListPresenter(eventsRemoteRepository)

    @Provides
    @Singleton
    fun provideEvenDetailPresenter(
        eventsRemoteRepository: EventsRemoteRepository
    ): EventDetailContract.Presenter = EventDetailPresenter(eventsRemoteRepository)

    @Provides
    @Singleton
    fun provideEvenCheckInPresenter(
        eventsRemoteRepository: EventsRemoteRepository
    ): CheckInContract.Presenter = CheckInPresenter(eventsRemoteRepository)


}
