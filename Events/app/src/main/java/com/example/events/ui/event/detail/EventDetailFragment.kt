package com.example.events.ui.event.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.events.R
import com.example.events.application.EventsApplication
import com.example.events.model.Event
import com.example.events.ui.event.checkIn.CheckInModalFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_event_detail.view.*
import kotlinx.android.synthetic.main.fragment_events_list.view.progress_circular
import kotlinx.android.synthetic.main.fragment_events_list.view.toolbar
import javax.inject.Inject


class EventDetailFragment : Fragment(), EventDetailContract.View {
    @Inject
    lateinit var presenter: EventDetailContract.Presenter
    private lateinit var rootView: View
    lateinit var progressBar: ProgressBar
    lateinit var toolbar: Toolbar
    private var eventId: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as EventsApplication).eventsComponent.inject(this)
        presenter.setView(this)
        rootView = inflater.inflate(R.layout.fragment_event_detail, container, false)

        progressBar = rootView.progress_circular
        toolbar = rootView.toolbar
        setToolbar()

        eventId = arguments?.getString("eventID")
        eventId?.let { presenter.getEvent(it) }
        return rootView
    }

    override fun bindEvent(event: Event?) {

        val txtTitle = rootView.tv_event_title
        val txtDesc = rootView.tv_event_description
        val txtPrice = rootView.tv_event_price
        val imageView = rootView.img_event
        val txtdate = rootView.tv_event_date
        val title = arguments?.getString("eventName")
        val btnShare = rootView.btn_share
        val fab = rootView.fab
        val btnCheckIn = rootView.btn_checkin

        presenter.bindTitle(title, txtTitle)
        presenter.bindDescription(event?.description, txtDesc)
        presenter.bindPrice(event?.price, txtPrice)
        presenter.bindDate(event?.date?.toInt(), txtdate)
        presenter.bindImage(event?.image, imageView)
        presenter.bindMapButton(requireContext(), fab, event?.latitude, event?.longitude)
        presenter.bindShareButton(requireContext(), btnShare, event)
        btnCheckIn.setOnClickListener { showCheckInModal(event?.id!!) }

    }

    override fun showCheckInModal(enventId: String) {
        val checkInModalFragment = CheckInModalFragment(eventId!!)
        checkInModalFragment.arguments = arguments
        checkInModalFragment.show(parentFragmentManager, "")
    }

    override fun displayLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progressBar.visibility = View.GONE
    }

    override fun setToolbar() {

        toolbar.setNavigationIcon(R.drawable.chevron_left)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    override fun displayError() {
        val msg = getString(R.string.error_event_msg)
        val snackbar: Snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG)
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onShown(snackbar: Snackbar) {
                super.onShown(snackbar)
            }

            override fun onDismissed(snackbar: Snackbar, event: Int) {
                super.onDismissed(snackbar, event)
                findNavController().popBackStack()
            }
        })
        snackbar.show()
    }

    companion object {
        private const val TAG = "EventDetailFragment"
    }
}