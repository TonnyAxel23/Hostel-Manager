package com.hollywoodhostels.hostelmanager.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.hollywoodhostels.hostelmanager.viewmodel.HostelViewModel
import com.hollywoodhostels.hostelmanager.viewmodel.HostelViewModelFactory
import com.hollywoodhostels.hostelmanager.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HostelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val factory = HostelViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[HostelViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {
        // Use lifecycleScope to collect Flow
        lifecycleScope.launch {
            viewModel.dashboardSummary.collect { summary ->
                summary?.let {
                    updateDashboardUI(it)
                }
            }
        }
    }

    private fun updateDashboardUI(summary: com.hollywoodhostels.hostelmanager.viewmodel.DashboardSummary) {
        // Update your UI with the summary data
        // Make sure these IDs exist in your fragment_dashboard.xml
        binding.tvTotalTenants.text = summary.totalTenants.toString()
        binding.tvTotalRent.text = "KSH ${summary.totalMonthlyRent}"
        binding.tvPaidTenants.text = summary.paidTenants.toString()
        binding.tvPendingTenants.text = summary.pendingTenants.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}