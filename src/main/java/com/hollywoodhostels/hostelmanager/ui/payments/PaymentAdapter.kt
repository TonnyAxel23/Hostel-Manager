package com.hollywoodhostels.hostelmanager.ui.payments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hollywoodhostels.hostelmanager.R
import com.hollywoodhostels.hostelmanager.data.entities.Payment
import java.text.SimpleDateFormat
import java.util.*



class PaymentAdapter : ListAdapter<Payment, PaymentAdapter.PaymentViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment, parent, false)
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = getItem(position)
        holder.bind(payment)
    }

    class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTenantName: TextView = itemView.findViewById(R.id.tv_tenant_name)
        private val tvAmount: TextView = itemView.findViewById(R.id.tv_amount)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvMethod: TextView = itemView.findViewById(R.id.tv_method)

        fun bind(payment: Payment) {
            // You'll need to load tenant name from tenantId - for now use ID
            tvTenantName.text = "Tenant ID: ${payment.tenantId}"
            tvAmount.text = "₹${payment.amount}"
            tvDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(payment.paymentDate)
            tvMethod.text = payment.paymentMethod
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Payment>() {
            override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
                return oldItem == newItem
            }
        }
    }
}