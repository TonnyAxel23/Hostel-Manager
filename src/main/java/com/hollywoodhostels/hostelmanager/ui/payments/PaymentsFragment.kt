package com.hollywoodhostels.hostelmanager.ui.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hollywoodhostels.hostelmanager.R
import com.google.android.material.button.MaterialButton
import java.util.*

class PaymentsFragment : Fragment() {

    private lateinit var viewModel: HostelViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaymentAdapter
    private lateinit var spinnerMonth: Spinner
    private lateinit var spinnerYear: Spinner
    private lateinit var btnRecordPayment: MaterialButton

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private val months = arrayOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HostelViewModel::class.java]
        initViews(view)
        setupSpinners()
        setupRecyclerView()
        setupClickListeners()
        loadPayments()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.rv_payments)
        spinnerMonth = view.findViewById(R.id.spinner_month)
        spinnerYear = view.findViewById(R.id.spinner_year)
        btnRecordPayment = view.findViewById(R.id.btn_record_payment)
    }

    private fun setupSpinners() {
        // Month spinner
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMonth.adapter = monthAdapter

        // Year spinner (current year and previous 2 years)
        val years = (currentYear - 2..currentYear).toList().reversed()
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter

        // Set current month and year
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        spinnerMonth.setSelection(currentMonth)
        spinnerYear.setSelection(0)

        // Add listeners
        spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                loadPayments()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                loadPayments()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupRecyclerView() {
        adapter = PaymentAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        btnRecordPayment.setOnClickListener {
            showRecordPaymentDialog()
        }
    }

    private fun loadPayments() {
        val month = spinnerMonth.selectedItem as String
        val year = spinnerYear.selectedItem as Int

        // This would need to be implemented in ViewModel to get payments by month/year
        // For now, we'll show all payments and filter later
        viewModel.tenants.observe(viewLifecycleOwner) { tenants ->
            // Load payments for the selected month/year
            // This is a simplified version - you'd need to implement proper payment loading
            val payments = mutableListOf<Payment>()
            adapter.submitList(payments)
        }
    }

    private fun showRecordPaymentDialog() {
        RecordPaymentDialog.newInstance().apply {
            setOnPaymentRecordedListener { payment ->
                viewModel.addPayment(payment)
                loadPayments()
            }
        }.show(parentFragmentManager, "record_payment")
    }
}