package com.hollywoodhostels.hostelmanager.ui.payments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.hollywoodhostels.hostelmanager.R
import androidx.fragment.app.DialogFragment
import java.util.*

class RecordPaymentDialog : DialogFragment() {

    private lateinit var spinnerTenants: Spinner
    private lateinit var etAmount: EditText
    private lateinit var spinnerPaymentMethod: Spinner
    private lateinit var btnDate: Button
    private var selectedDate = Calendar.getInstance()
    private var onPaymentRecordedListener: ((Payment) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_record_payment, null)

        initViews(view)
        setupClickListeners()

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle("Record Payment")
            .setPositiveButton("Save") { _, _ ->
                savePayment()
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun initViews(view: View) {
        spinnerTenants = view.findViewById(R.id.spinner_tenants)
        etAmount = view.findViewById(R.id.et_amount)
        spinnerPaymentMethod = view.findViewById(R.id.spinner_payment_method)
        btnDate = view.findViewById(R.id.btn_payment_date)

        setupSpinners()
        updateDateButton()
    }

    private fun setupSpinners() {
        // Payment methods
        val paymentMethods = arrayOf("Cash", "Bank Transfer", "UPI", "Cheque")
        val methodAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, paymentMethods)
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPaymentMethod.adapter = methodAdapter

        // Tenants would be loaded from database
        // For now, using placeholder
        val tenantAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf("Select Tenant"))
        tenantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTenants.adapter = tenantAdapter
    }

    private fun setupClickListeners() {
        btnDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                updateDateButton()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateButton() {
        btnDate.text = SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time)
    }

    private fun savePayment() {
        val amount = etAmount.text.toString().toDoubleOrNull()
        val selectedTenantPosition = spinnerTenants.selectedItemPosition

        if (amount == null || amount <= 0) {
            Toast.makeText(requireContext(), "Please enter valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedTenantPosition == 0) {
            Toast.makeText(requireContext(), "Please select a tenant", Toast.LENGTH_SHORT).show()
            return
        }

        val payment = Payment(
            tenantId = 1L, // This should be the actual tenant ID
            amount = amount,
            paymentDate = selectedDate.time,
            month = selectedDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: "",
            year = selectedDate.get(Calendar.YEAR),
            paymentMethod = spinnerPaymentMethod.selectedItem as String
        )

        onPaymentRecordedListener?.invoke(payment)
        dismiss()
    }

    fun setOnPaymentRecordedListener(listener: (Payment) -> Unit) {
        onPaymentRecordedListener = listener
    }

    companion object {
        fun newInstance(): RecordPaymentDialog {
            return RecordPaymentDialog()
        }
    }
}