package com.example.events.ui.event.checkIn

import com.example.events.model.User
import com.example.events.network.remote.EventsRemoteRepository
import com.example.events.network.remote.response.GenericListener
import javax.inject.Inject


class CheckInPresenter @Inject constructor(
    private val eventsRemoteRepository: EventsRemoteRepository
) : CheckInContract.Presenter {

    private lateinit var view: CheckInContract.View


    override fun setView(view: CheckInContract.View) {
        this.view = view;
    }

    override fun checkIn(user: User) {
        view.displayLoading();
        eventsRemoteRepository.checkIn(user, object : GenericListener {
            override fun onSuccess() {
                view.dismissLoading()
                view.dismissModal()
            }

            override fun onError() {
                view.dismissLoading()
                view.displayError()
            }
        })
    }


    companion object {
        const val TAG = "CheckInPresenter"
    }
}