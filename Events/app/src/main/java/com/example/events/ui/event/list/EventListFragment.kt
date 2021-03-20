package com.example.events.ui.event.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.events.R
import com.example.events.application.EventsApplication
import com.example.events.model.Event
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_events_list.view.*
import javax.inject.Inject


class EventListFragment : Fragment(), EventListContract.View, EventListContract.Adapter {
    @Inject
    lateinit var presenter: EventListContract.Presenter
    private lateinit var rootView: View
    lateinit var progressBar: ProgressBar
    lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as EventsApplication).eventsComponent.inject(this)
        presenter.setView(this)
        rootView = inflater.inflate(R.layout.fragment_events_list, container, false)

        progressBar = rootView.progress_circular
        toolbar = rootView.toolbar
        setToolbar()

        presenter.listEvents()
        return rootView
    }


    override fun setAdapter(events: List<Event>?) {
        val adapter = EventListAdapter(events, requireContext(), this)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun displayLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progressBar.visibility = View.GONE
    }

    override fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.chevron_left)
        toolbar.setNavigationOnClickListener { activity?.finish() }
    }

    override fun displayError() {
        val msg = getString(R.string.error_list_events_msg)
        Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG)
            .setAction("Ok", null)
            .show()
    }

    override fun navigateToEventDetail(title: String, eventId: String) {
        findNavController().navigate(
            EventListFragmentDirections.actionEventFragmentToDetailFragment(
                title,
                eventId
            )
        )
    }


    companion object {
        private const val TAG = "EventListFragment"
    }
}