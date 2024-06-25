package com.example.mymechinelearning.ui.fitur

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymechinelearning.R // Replace with your package name

class PiturAdapter(private val dataset: List<PiturItem>) :
    RecyclerView.Adapter<PiturAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvColumn: TextView = view.findViewById(R.id.tv_column)
        val tvDimensi: TextView = view.findViewById(R.id.tv_dimensi)
        val tvPenjelasan: TextView = view.findViewById(R.id.tv_penjelasan)
        val tvTypeData: TextView = view.findViewById(R.id.typeData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dataset, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.tvColumn.text = item.column
        holder.tvDimensi.text = item.dimensi.toString()
        holder.tvPenjelasan.text = item.penjelasan
        holder.tvTypeData.text = item.typeData
    }

    override fun getItemCount() = dataset.size
}