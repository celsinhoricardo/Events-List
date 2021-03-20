package com.example.events.dagger

import com.example.event.dagger.NetworkModule
import com.example.event.dagger.PresenterModule
import com.example.events.ui.event.checkIn.CheckInModalFragment
import com.example.events.ui.event.detail.EventDetailFragment
import com.example.events.ui.event.list.EventListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        PresenterModule::class,
        NetworkModule::class]
)

interface AppComponent {
    fun inject(target: EventListFragment)
    fun inject(target: EventDetailFragment)
    fun inject(target: CheckInModalFragment)

}