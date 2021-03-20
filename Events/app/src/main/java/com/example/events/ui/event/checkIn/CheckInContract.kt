package com.example.events.ui.event.checkIn

import com.example.event.ui.BasePresenter
import com.example.events.model.User


interface CheckInContract {

    interface Presenter : BasePresenter<View> {
        fun checkIn(user: User)
    }

    interface View {
        fun dismissModal()
        fun displayLoading()
        fun dismissLoading()
        fun displayError()
    }

}
