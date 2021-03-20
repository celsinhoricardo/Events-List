package com.example.events.ui.event.list

import com.example.event.ui.BasePresenter
import com.example.event.ui.BaseView
import com.example.events.model.Event


interface EventListContract {

    interface Presenter : BasePresenter<View> {
        fun listEvents()

    }

    interface View : BaseView<Presenter> {
        fun displayLoading()
        fun dismissLoading()
        fun setAdapter(events: List<Event>?)

    }

    interface Adapter {
        fun navigateToEventDetail(title: String, eventId: String)
    }
}
