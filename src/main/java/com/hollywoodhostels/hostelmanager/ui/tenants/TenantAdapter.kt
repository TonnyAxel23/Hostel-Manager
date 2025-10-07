package com.hollywoodhostels.hostelmanager.ui.tenants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hollywoodhostels.hostelmanager.R
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class TenantAdapter(private val onItemClick: (Tenant) -> Unit) :
    ListAdapter<Tenant, TenantAdapter.TenantViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tenant, parent, false)
        return TenantViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TenantViewHolder(itemView: View, private val onItemClick: (Tenant) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvRoomNumber: TextView = itemView.findViewById(R.id.tv_room_number)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val tvRent: TextView = itemView.findViewById(R.id.tv_rent)
        private val tvCheckIn: TextView = itemView.findViewById(R.id.tv_check_in)

        fun bind(tenant: Tenant) {
            tvName.text = tenant.name
            tvRoomNumber.text = "Room: ${tenant.roomNumber}"
            tvPhone.text = tenant.phoneNumber
            tvRent.text = "Rent: ₹${tenant.rentAmount}"
            tvCheckIn.text = "Check-in: ${SimpleDateFormat("dd/MM/yyyy").format(tenant.checkInDate)}"

            itemView.setOnClickListener {
                onItemClick(tenant)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Tenant>() {
        override fun areItemsTheSame(oldItem: Tenant, newItem: Tenant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tenant, newItem: Tenant): Boolean {
            return oldItem == newItem
        }
    }
}