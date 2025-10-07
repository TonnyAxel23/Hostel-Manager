package com.hollywoodhostels.hostelmanager.ui.tenants

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.hollywoodhostels.hostelmanager.R
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class TenantsFragment : Fragment() {

    private lateinit var viewModel: HostelViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TenantAdapter
    private lateinit var btnAddTenant: MaterialButton
    private lateinit var btnExportTenants: MaterialButton
    private lateinit var etSearch: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tenants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HostelViewModel::class.java]
        initViews(view)
        setupRecyclerView()
        setupClickListeners()
        observeTenants()
        setupSearch()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.rv_tenants)
        btnAddTenant = view.findViewById(R.id.btn_add_tenant)
        btnExportTenants = view.findViewById(R.id.btn_export_tenants)
        etSearch = view.findViewById(R.id.et_search)
    }

    private fun setupRecyclerView() {
        adapter = TenantAdapter { tenant ->
            showTenantDetails(tenant)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        btnAddTenant.setOnClickListener {
            findNavController().navigate(R.id.addTenantFragment)
        }

        btnExportTenants.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val tenants = viewModel.tenants.value ?: emptyList()
                val file = ExcelExporter.exportTenantsData(requireContext(), tenants)

                CoroutineScope(Dispatchers.Main).launch {
                    shareFile(file, "Tenants Data")
                }
            }
        }
    }

    private fun observeTenants() {
        viewModel.tenants.observe(viewLifecycleOwner) { tenants ->
            adapter.submitList(tenants)
        }
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterTenants(s.toString())
            }
        })
    }

    private fun filterTenants(query: String) {
        viewModel.tenants.observe(viewLifecycleOwner) { tenants ->
            val filtered = if (query.isBlank()) {
                tenants
            } else {
                tenants.filter { tenant ->
                    tenant.name.contains(query, true) ||
                            tenant.roomNumber.contains(query, true) ||
                            tenant.phoneNumber.contains(query, true)
                }
            }
            adapter.submitList(filtered)
        }
    }

    private fun showTenantDetails(tenant: Tenant) {
        // Implement tenant details dialog or fragment
        TenantDetailsDialog.newInstance(tenant.id)
            .show(parentFragmentManager, "tenant_details")
    }

    private fun shareFile(file: File, title: String) {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(shareIntent, "Share $title"))
    }
}