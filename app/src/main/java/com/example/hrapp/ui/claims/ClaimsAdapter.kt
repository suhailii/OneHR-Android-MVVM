package com.example.hrapp.ui.claims

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
import data.Claim


class ClaimsAdapter(private val claim: ArrayList<Claim>) : RecyclerView.Adapter<ClaimsAdapter.ClaimViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimsAdapter.ClaimViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_claims, parent, false) as View

        return ClaimViewHolder(itemView, this)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ClaimViewHolder, position: Int) {

        // sets the text to the textview from our itemHolder class
        holder.tvClaims.text = claim[position].type
        holder.tvAmount.text = "Amount:$ " + claim[position].amount
        if(claim[position].status == "NOTIFIEDA"){
            holder.tvStatus.text = "Approved"
            holder.tvStatus.setTextColor(Color.parseColor("#008000"))
        }else if (claim[position].status == "NOTIFIEDR"){
            holder.tvStatus.text = "Rejected"
            holder.tvStatus.setTextColor(Color.parseColor("#FF0000"))
        }else{
            holder.tvStatus.text = claim[position].status
            holder.tvStatus.setTextColor(Color.parseColor("#FF7F00"))
        }
        if (claim[position].type == "Transport"){
            holder.icon.setImageResource(R.drawable.ic_transport)
        }else if (claim[position].type == "Medical"){
            holder.icon.setImageResource(R.drawable.ic_medical)
        }else if (claim[position].type == "Stationary"){
            holder.icon.setImageResource(R.drawable.ic_stationary)
        }else if (claim[position].type == "Adhoc"){
            holder.icon.setImageResource(R.drawable.ic_adhoc)
        }

        holder.tvExtra.setOnClickListener{
            val action = ClaimsListFragmentDirections.actionClaimsListFragmentToSelectedClaimFragment(claim[position].id.toString())
            it.findNavController().navigate(action)
        }

    }
    override fun getItemCount(): Int {
        return claim.size
    }


    // Holds the views for adding it to image and text
    class ClaimViewHolder(itemView: View, adapter: ClaimsAdapter) : RecyclerView.ViewHolder(itemView) {
        val tvClaims : TextView = itemView.findViewById(R.id.recyclerClaims)
        val tvAmount : TextView = itemView.findViewById(R.id.recyclerClaimsAmount)
        val tvStatus : TextView = itemView.findViewById(R.id.recyclerStatus)
        val tvExtra : LinearLayout = itemView.findViewById(R.id.recyclerClaimsExtra)
        val icon : ImageView = itemView.findViewById(R.id.claimsIcon)
    }


}