package com.example.events.ui.event.list;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.events.R
import com.example.events.model.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_list_item.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(
    val items: List<Event>?,
    val context: Context,
    val view: EventListContract.Adapter
) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        if (items != null) {
            return items.size
        }
        return 0;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items?.get(position)
        if (item != null) {
            holder?.title.setText(item.title)
            bindPrice(item.price, holder?.price)
            bindDate(item.date?.toInt(), holder?.date)
            bindImage(item.image, holder?.image, holder?.progressBar)

            holder?.btnDetail.setOnClickListener {
                view.navigateToEventDetail(item.title!!, item.id!!)
            }
        }

    }


    fun bindPrice(price: String?, textView: TextView) {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val price = format.format(price?.toDouble())
        textView.setText(price)
    }

    fun bindDate(date: Int?, textView: TextView) {
        val formatDate = SimpleDateFormat("DD MMM")
        textView.setText(formatDate.format(date))
    }

    fun bindImage(imageUrl: String, imageView: ImageView, progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(imageUrl)
            .into(imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: java.lang.Exception?) {
                    imageView.setImageResource(R.drawable.noimage)
                    progressBar.visibility = View.GONE
                }
            })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.tv_event_title
        val image = itemView.img_event
        val price = itemView.tv_event_price
        val date = itemView.tv_event_date
        val btnDetail = itemView.btn_detail

        val progressBar = itemView.progress_circular

        /* init {
             itemView.setOnClickListener { view.navigateToEventDetail() }
         }*/

    }
}
