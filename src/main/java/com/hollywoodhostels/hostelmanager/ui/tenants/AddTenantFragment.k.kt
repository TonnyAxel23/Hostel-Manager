package com.hollywoodhostels.hostelmanager.ui.tenants

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.hollywoodhostels.hostelmanager.R
import androidx.lifecycle.ViewModelProvider
import java.util.*

class AddTenantFragment : Fragment() {

    private lateinit var viewModel: HostelViewModel
    private lateinit var etName: EditText
    private lateinit var etRoomNumber: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etRentAmount: EditText
    private lateinit var etSecurityDeposit: EditText
    private lateinit var btnSelectDate: Button
    private lateinit var btnSave: Button

    private var selectedDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_tenant, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        etName = view.findViewById(R.id.et_name)
        etRoomNumber = view.findViewById(R.id.et_room_number)
        etPhone = view.findViewById(R.id.et_phone)
        etEmail = view.findViewById(R.id.et_email)
        etRentAmount = view.findViewById(R.id.et_rent_amount)
        etSecurityDeposit = view.findViewById(R.id.et_security_deposit)
        btnSelectDate = view.findViewById(R.id.btn_select_date)
        btnSave = view.findViewById(R.id.btn_save_tenant)

        viewModel = ViewModelProvider(this)[HostelViewModel::class.java]

        setupClickListeners()
    }

    private fun setupClickListeners() {
        btnSelectDate.setOnClickListener {
            showDatePicker()
        }

        btnSave.setOnClickListener {
            saveTenant()
        }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                btnSelectDate.text = "${dayOfMonth}/${month + 1}/$year"
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun saveTenant() {
        val name = etName.text.toString().trim()
        val roomNumber = etRoomNumber.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val rentAmount = etRentAmount.text.toString().toDoubleOrNull() ?: 0.0
        val securityDeposit = etSecurityDeposit.text.toString().toDoubleOrNull() ?: 0.0

        if (name.isEmpty() || roomNumber.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val tenant = Tenant(
            name = name,
            roomNumber = roomNumber,
            phoneNumber = phone,
            email = email,
            checkInDate = selectedDate.time,
            rentAmount = rentAmount,
            securityDeposit = securityDeposit
        )

        viewModel.addTenant(tenant)
        Toast.makeText(requireContext(), "Tenant added successfully", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }
}