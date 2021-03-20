package com.example.events.application

import androidx.multidex.MultiDexApplication
import com.example.events.dagger.AppComponent
import com.example.events.dagger.AppModule
import com.example.events.dagger.DaggerAppComponent


class EventsApplication : MultiDexApplication() {
    lateinit var eventsComponent: AppComponent

    private fun initDagger(app: EventsApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    override fun onCreate() {
        super.onCreate()
        eventsComponent = initDagger(this)
    }
}