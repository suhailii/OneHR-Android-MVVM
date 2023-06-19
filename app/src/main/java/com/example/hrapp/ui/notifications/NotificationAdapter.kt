package com.example.hrapp.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hrapp.R

import data.Notification


class NotificationAdapter(private val notif: ArrayList<Notification>) : RecyclerView.Adapter<NotificationAdapter.WordViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.WordViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_notif, parent, false) as View

        return WordViewHolder(itemView, this)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        // sets the text to the textview from our itemHolder class
        holder.title.text = notif[position].title
        holder.body.text = notif[position].body
        if (notif[position].title.contains("In")){
            holder.img.setImageResource(R.drawable.greentick)
        }
        else if (notif[position].title.contains("Out")) {
            holder.img.setImageResource(R.drawable.cross)
        }
        else if (notif[position].title.contains("Annual")) {
            holder.img.setImageResource(R.drawable.ic_annual)
        }else if (notif[position].title.contains("Childcare")) {
            holder.img.setImageResource(R.drawable.ic_childcare)
        }else if (notif[position].title.contains("Sick")) {
            holder.img.setImageResource(R.drawable.ic_sick)
        }else if (notif[position].title.contains("Maternity")) {
            holder.img.setImageResource(R.drawable.ic_maternity)
        }
        else if (notif[position].title.contains("Transport")) {
            holder.img.setImageResource(R.drawable.ic_transport)
        }else if (notif[position].title.contains("Medical")) {
            holder.img.setImageResource(R.drawable.ic_medical)
        }else if (notif[position].title.contains("Stationary")) {
            holder.img.setImageResource(R.drawable.ic_stationary)
        }else if (notif[position].title.contains("Adhoc")) {
            holder.img.setImageResource(R.drawable.ic_adhoc)
        }
        else if (notif[position].title.contains("Room")) {
            holder.img.setImageResource(R.drawable.jitsi)
        }

        holder.notif.setOnClickListener{
            if(holder.title.text.contains("Leave")){
                Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_leavesListFragment)
            }
            else if (holder.title.text.contains("Claim")){
                Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_claimsListFragment)
            }
            else if (holder.title.text.contains("Room")){
                Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_navigation_jitsi)
            }
            else if (holder.title.text.contains("Checked")){
                Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_navigation_home)
            }
        }

        // Sets onclicklistener
        /*holder.title.setOnClickListener {
            if (holder.notif.text.contains("leave")){
                Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_navigation_leave)
            }
            else if (holder.notif.text.contains("claim")){
                Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_navigation_claims)
            }
            else{
                Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_navigation_home)
            }
        }*/

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return notif.size
    }

    // Holds the views for adding it to image and text
    class WordViewHolder(itemView: View, adapter: NotificationAdapter) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById<TextView>(R.id.recyclerNotif)
        val body: TextView = itemView.findViewById<TextView>(R.id.recyclerTime)
        val img: ImageView = itemView.findViewById<ImageView>(R.id.notifIcon)
        val notif : LinearLayout = itemView.findViewById(R.id.linearLayoutNotification)

    }
}