package com.example.events.ui.event.list

import com.example.events.model.Event
import com.example.events.network.remote.EventsRemoteRepository
import com.example.events.network.remote.response.EventsListener
import javax.inject.Inject


class EventListPresenter @Inject constructor(
    private val eventsRemoteRepository: EventsRemoteRepository
) : EventListContract.Presenter {

    private lateinit var view: EventListContract.View


    override fun listEvents() {
        view.displayLoading();
        eventsRemoteRepository.listEvents(object : EventsListener {
            override fun onSuccess(events: List<Event>?) {
                view.dismissLoading()
                view.setAdapter(events)
            }

            override fun onError() {
                view.dismissLoading()
                view.displayError()
            }
        })
    }

    override fun setView(view: EventListContract.View) {
        this.view = view;
    }


    companion object {
        const val TAG = "EventListPresenter"
    }
}