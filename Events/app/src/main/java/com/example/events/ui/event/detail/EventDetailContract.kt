package com.example.events.ui.event.detail

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.example.event.ui.BasePresenter
import com.example.event.ui.BaseView
import com.example.events.model.Event
import com.google.android.material.floatingactionbutton.FloatingActionButton


interface EventDetailContract {

    interface Presenter : BasePresenter<View> {
        fun getEvent(eventId: String)

        fun bindTitle(title: String?, textView: TextView)
        fun bindDescription(description: String?, textView: TextView)
        fun bindPrice(price: String?, textView: TextView)
        fun bindDate(date: Int?, textView: TextView)
        fun bindImage(imageUrl: String?, imageView: ImageView)
        fun bindMapButton(
            context: Context,
            button: FloatingActionButton,
            latitute: String?,
            longitude: String?
        )

        fun bindShareButton(context: Context, button: ImageView, event: Event?)
    }

    interface View : BaseView<Presenter> {
        fun displayLoading()
        fun dismissLoading()
        fun bindEvent(event: Event?)
        fun showCheckInModal(enventId: String)

    }

}
