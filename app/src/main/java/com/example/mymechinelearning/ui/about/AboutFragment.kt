package com.example.mymechinelearning.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymechinelearning.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val aboutViewModel =
            ViewModelProvider(this).get(AboutViewModel::class.java)

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewJudul: TextView = binding.hai
        val textViewNama: TextView = binding.selamatDattang
        val textViewDeksripisi: TextView = binding.appName

        aboutViewModel.textjudul.observe(viewLifecycleOwner) {
            textViewJudul.text = it
        }
        aboutViewModel.textnama.observe(viewLifecycleOwner) {
            textViewNama.text = it
        }
        aboutViewModel.textdeksripsi.observe(viewLifecycleOwner) {
            textViewDeksripisi.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}