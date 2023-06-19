package com.example.hrapp.ui.managerview

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

class ManagerLeaveAdapter(private val leave: ArrayList<Leave>) : RecyclerView.Adapter<ManagerLeaveAdapter.LeaveViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerLeaveAdapter.LeaveViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_managerleaves, parent, false) as View

        return LeaveViewHolder(itemView, this)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: LeaveViewHolder, position: Int) {

        // sets the text to the textview from our itemHolder class
        holder.tvNames.text = leave[position].name
        holder.tvLeave.text = leave[position].type
        holder.tvStart.text = "From: " + leave[position].startDate
        holder.tvEnd.text = "Until: " + leave[position].endDate
        if (leave[position].type == "Annual Leave"){
            holder.icon.setImageResource(R.drawable.ic_annual)
        }else if (leave[position].type == "Childcare Leave"){
            holder.icon.setImageResource(R.drawable.ic_childcare)
        }else if (leave[position].type == "Sick Leave"){
            holder.icon.setImageResource(R.drawable.ic_sick)
        }else if (leave[position].type == "Maternity Leave"){
            holder.icon.setImageResource(R.drawable.ic_maternity)
        }


        holder.ibNext.setOnClickListener {
            val action = ManageLeaveListFragmentDirections.actionManageLeaveListFragmentToManagerLeaveSelectFragment(leave[position].id.toString())
            it.findNavController().navigate(action)
        }




    }
    override fun getItemCount(): Int {
        return leave.size
    }


    // Holds the views for adding it to image and text
    class LeaveViewHolder(itemView: View, adapter: ManagerLeaveAdapter) : RecyclerView.ViewHolder(itemView) {
        val tvNames : TextView = itemView.findViewById(R.id.recyclerNameLeave)
        val tvLeave : TextView = itemView.findViewById(R.id.recyclerLeave)
        val tvStart : TextView = itemView.findViewById(R.id.recyclerStart)
        val tvEnd : TextView = itemView.findViewById(R.id.recyclerEnd)
        val ibNext : LinearLayout = itemView.findViewById(R.id.recyclerLeaveExtra)
        val icon : ImageView = itemView.findViewById(R.id.managerLeaveIcon)
    }


}