package com.example.hrapp.ui.leaves

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hrapp.R
import data.Leave


class LeavesAdapter(private val leave: ArrayList<Leave>) :
    RecyclerView.Adapter<LeavesAdapter.LeaveViewHolder>() {

    // create new views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeavesAdapter.LeaveViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_leaves, parent, false) as View

        return LeaveViewHolder(itemView, this)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: LeaveViewHolder, position: Int) {

        // sets the text to the textview from our itemHolder class
        holder.tvleaves.text = leave[position].type
        holder.tvStart.text = "From: " + leave[position].startDate
        holder.tvEnd.text = "Until: " + leave[position].endDate
        if (leave[position].status == "NOTIFIEDA"){
            holder.tvStatus.text = "Approved"
            holder.tvStatus.setTextColor(Color.parseColor("#008000"))
        }else if (leave[position].status == "NOTIFIEDR"){
            holder.tvStatus.text = "Rejected"
            holder.tvStatus.setTextColor(Color.parseColor("#FF0000"))
        }else{
            holder.tvStatus.text = leave[position].status
            holder.tvStatus.setTextColor(Color.parseColor("#FF7F00"))
        }
        if (leave[position].type == "Annual Leave"){
            holder.icon.setImageResource(R.drawable.ic_annual)
        }else if (leave[position].type == "Childcare Leave"){
            holder.icon.setImageResource(R.drawable.ic_childcare)
        }else if (leave[position].type == "Sick Leave"){
            holder.icon.setImageResource(R.drawable.ic_sick)
        }else if (leave[position].type == "Maternity Leave"){
            holder.icon.setImageResource(R.drawable.ic_maternity)
        }
        holder.tvExtra.setOnClickListener {
            val action =
                LeavesListFragmentDirections.actionLeavesListFragmentToSelectedLeaveFragment(leave[position].id.toString())
            it.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return leave.size
    }


    // Holds the views for adding it to image and text
    class LeaveViewHolder(itemView: View, adapter: LeavesAdapter) :
        RecyclerView.ViewHolder(itemView) {
        val tvleaves: TextView = itemView.findViewById(R.id.recyclerLeaves)
        val tvStart: TextView = itemView.findViewById(R.id.recyclerStartDate)
        val tvEnd: TextView = itemView.findViewById(R.id.recyclerEndDate)
        val tvStatus: TextView = itemView.findViewById(R.id.recyclerStatus)
        val tvExtra: LinearLayout = itemView.findViewById(R.id.recyclerLeaveExtra)
        val icon : ImageView = itemView.findViewById(R.id.leaveIcon)
    }


}