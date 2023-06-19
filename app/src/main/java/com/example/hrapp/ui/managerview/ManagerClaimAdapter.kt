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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import data.Claim

class ManagerClaimAdapter(private val claim: ArrayList<Claim>) : RecyclerView.Adapter<ManagerClaimAdapter.ClaimViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerClaimAdapter.ClaimViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_managerclaims, parent, false) as View

        return ClaimViewHolder(itemView, this)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ClaimViewHolder, position: Int) {

        // sets the text to the textview from our itemHolder class
        holder.tvNames.text = claim[position].name
        holder.tvClaim.text = claim[position].type
        holder.tvCost.text = claim[position].amount
        holder.tvRemarks.text = claim[position].remarks

        if (claim[position].type == "Transport"){
            holder.icon.setImageResource(R.drawable.ic_transport)
        }else if (claim[position].type == "Medical"){
            holder.icon.setImageResource(R.drawable.ic_medical)
        }else if (claim[position].type == "Stationary"){
            holder.icon.setImageResource(R.drawable.ic_stationary)
        }else if (claim[position].type == "Adhoc"){
            holder.icon.setImageResource(R.drawable.ic_adhoc)
        }



        holder.ibNext.setOnClickListener {
            val action = ManageClaimListFragmentDirections.actionManageClaimListFragmentToManagerClaimSelectFragment(claim[position].id.toString())
            it.findNavController().navigate(action)
        }




    }
    override fun getItemCount(): Int {
        return claim.size
    }


    // Holds the views for adding it to image and text
    class ClaimViewHolder(itemView: View, adapter: ManagerClaimAdapter) : RecyclerView.ViewHolder(itemView) {
        val tvNames : TextView = itemView.findViewById(R.id.recyclerName)
        val tvClaim : TextView = itemView.findViewById(R.id.recyclerClaim)
        val tvCost : TextView = itemView.findViewById(R.id.recyclerCost)
        val tvRemarks : TextView = itemView.findViewById(R.id.recyclerRemarks)
        val ibNext : LinearLayout = itemView.findViewById(R.id.recyclerClaimExtra)
        val icon : ImageView = itemView.findViewById(R.id.managerClaimIcon)

    }


}