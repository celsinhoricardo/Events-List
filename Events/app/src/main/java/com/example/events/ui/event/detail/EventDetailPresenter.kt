package com.example.events.ui.event.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.events.R
import com.example.events.model.Event
import com.example.events.network.remote.EventsRemoteRepository
import com.example.events.network.remote.response.EventListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class EventDetailPresenter @Inject constructor(
    private val eventsRemoteRepository: EventsRemoteRepository
) : EventDetailContract.Presenter {

    private lateinit var view: EventDetailContract.View

    override fun getEvent(eventId: String) {
        view.displayLoading();
        eventsRemoteRepository.getEvent(eventId, object : EventListener {
            override fun onSuccess(event: Event?) {
                view.dismissLoading()
                view.bindEvent(event)

            }

            override fun onError() {
                view.dismissLoading()
                view.displayError()
            }
        })
    }

    override fun setView(view: EventDetailContract.View) {
        this.view = view;
    }


    override fun bindTitle(title: String?, textView: TextView) {
        textView.setText(title)
    }

    override fun bindDescription(description: String?, textView: TextView) {
        textView.setText(description)
    }

    override fun bindPrice(price: String?, textView: TextView) {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val price = format.format(price?.toDouble())
        textView.setText(price)
    }

    override fun bindDate(date: Int?, textView: TextView) {
        val formatDate = SimpleDateFormat("DD MMM yyyy")
        textView.setText(formatDate.format(date))
    }

    override fun bindImage(imageUrl: String?, imageView: ImageView) {

        Picasso.get()
            .load(imageUrl)
            .into(imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                }

                override fun onError(e: java.lang.Exception?) {
                    imageView.visibility = View.GONE
                }
            })
    }

    override fun bindMapButton(
        context: Context,
        button: FloatingActionButton,
        latitute: String?,
        longitude: String?
    ) {
        button.setOnClickListener { view ->
            openGPS(context, latitute!!.toDouble(), longitude!!.toDouble())
        }
    }


    fun openGPS(context: Context, lat: Double, long: Double) {
        val latitude = lat.toString()
        val longitude = long.toString()

        val url = "waze://?ll=" + latitude + ", " + longitude + "&navigate=yes"
        var intentWaze = Intent(Intent.ACTION_VIEW, Uri.parse(url)).setPackage("com.waze")

        val uriGoogle = "google.navigation:q=" + latitude + "," + longitude
        var intentGoogleNav = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uriGoogle)
        ).setPackage("com.google.android.apps.maps")

        val title: String = context.getString(R.string.app_name)
        val chooserIntent = Intent.createChooser(intentGoogleNav, title)
        val arr = arrayOfNulls<Intent>(1)
        arr[0] = intentWaze
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arr)
        context.startActivity(chooserIntent)
    }

    override fun bindShareButton(context: Context, button: ImageView, event: Event?) {
        button.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, event?.description);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, event?.title);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val chooserTitle = context.getString(R.string.chooser_share_title)
            startActivity(context, Intent.createChooser(shareIntent, chooserTitle), null)
        }
    }


    companion object {
        const val TAG = "EventDetailPresenter"
    }
}