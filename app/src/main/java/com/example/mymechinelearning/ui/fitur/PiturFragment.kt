package com.example.mymechinelearning.ui.fitur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymechinelearning.R
import com.example.mymechinelearning.databinding.FragmentPiturBinding
import com.example.mymechinelearning.ui.dataset.DatasetViewAdapter

class PiturFragment : Fragment() {

    private var _binding: FragmentPiturBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val piturViewModel = ViewModelProvider(this).get(PiturViewModel::class.java)

        _binding = FragmentPiturBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvPitur
        piturViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recycelerView
        val resources = requireContext().resources

        val arrayColumn = resources.getStringArray(R.array.array_column)
        val arrayDimensi = resources.getIntArray(R.array.array_dimensi)
        val arrayPenjelasan = resources.getStringArray(R.array.array_penjelasan)
        val arrayTypeData = resources.getStringArray(R.array.array_typeData)

        // Create a list of DatasetItem objects
        val dataset = mutableListOf<PiturItem>()
        for (i in arrayColumn.indices) {
            dataset.add(
                PiturItem(
                    arrayColumn[i],
                    arrayDimensi[i],
                    arrayPenjelasan[i],
                    arrayTypeData[i]
                )
            )
        }

        // Set up the RecyclerView
        recyclerView.adapter = PiturAdapter(dataset)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}