package com.example.events.ui.event.checkIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.events.R
import com.example.events.application.EventsApplication
import com.example.events.model.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.checkin_modal.view.*
import javax.inject.Inject


class CheckInModalFragment constructor(
    private val eventId: String
) : BottomSheetDialogFragment(), CheckInContract.View {

    @Inject
    lateinit var presenter: CheckInContract.Presenter

    private lateinit var rootView: View
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.application as EventsApplication).eventsComponent.inject(this)
        presenter.setView(this)
        rootView = inflater.inflate(R.layout.checkin_modal, container, false)
        progressBar = rootView.progress_circular

        rootView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dismissAllowingStateLoss()
        }
        rootView.findViewById<Button>(R.id.btn_confirm).setOnClickListener {

            val name = rootView.et_name.text.toString()
            val email = rootView.et_email.text.toString()
            if (!name.isNullOrEmpty() && !email.isNullOrEmpty()) {
                presenter.checkIn(User(eventId, name, email))
            }
        }

        return rootView
    }


    override fun displayError() {
        val msg = getString(R.string.error_checkin_msg)
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun dismissModal() {
        dismissAllowingStateLoss()
    }

    override fun displayLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progressBar.visibility = View.GONE
    }

    companion object {
        private const val TAG = "CheckInModalFragment"
    }

}