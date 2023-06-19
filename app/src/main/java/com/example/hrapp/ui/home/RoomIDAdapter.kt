package com.example.hrapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hrapp.R

class RoomIDAdapter(private val roomID: String) : RecyclerView.Adapter<RoomIDAdapter.RoomIDViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomIDAdapter.RoomIDViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_jitsi, parent, false) as View

        return RoomIDViewHolder(itemView, this)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: RoomIDViewHolder, position: Int) {
        // sets the text to the textview from our itemHolder class
        holder.tvJitsi.text = roomID

    }
    override fun getItemCount(): Int {
        return 1
    }


    // Holds the views for adding it to image and text
    class RoomIDViewHolder(itemView: View, adapter: RoomIDAdapter) : RecyclerView.ViewHolder(itemView) {
        val tvJitsi : TextView = itemView.findViewById(R.id.tvJitsi)
    }


}