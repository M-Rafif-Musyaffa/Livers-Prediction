package com.example.mymechinelearning.ui.dataset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymechinelearning.R
import com.example.mymechinelearning.databinding.FragmentDatasetBinding

class DatasetFragment : Fragment() {

    private var _binding: FragmentDatasetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val datasetViewModel =
            ViewModelProvider(this).get(DatasetViewModel::class.java)

        _binding = FragmentDatasetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvDataset
        datasetViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Initialize RecyclerView
        val recyclerView = binding.rvDataset
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Load data from resources
        val arrayColumn = resources.getStringArray(R.array.array_column)
        val arrayDimensi = resources.getIntArray(R.array.array_dimensi)
        val arrayTypeData = resources.getStringArray(R.array.array_typeData)

        val tableRows = arrayColumn.indices.map { index ->
            DatasetItem(
                columnName = arrayColumn[index],
                columnDimensi = arrayDimensi[index],
                columnTypeData = arrayTypeData[index]
            )
        }

        // Set adapter to RecyclerView
        recyclerView.adapter = DatasetViewAdapter(tableRows)

        // Add divider
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        return root

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}