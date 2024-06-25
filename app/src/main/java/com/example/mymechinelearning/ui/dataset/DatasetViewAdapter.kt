package com.example.mymechinelearning.ui.dataset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymechinelearning.R

class DatasetViewAdapter(private val tableRows: List<DatasetItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1

    class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val columnName: TextView = itemView.findViewById(R.id.columnName)
        val columnDimensi: TextView = itemView.findViewById(R.id.columnDimensi)
        val columnTypeData: TextView = itemView.findViewById(R.id.columnTypeData)
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerColumnName: TextView = itemView.findViewById(R.id.headerColumnName)
        val headerColumnDimensi: TextView = itemView.findViewById(R.id.headerColumnDimensi)
        val headerColumnTypeData: TextView = itemView.findViewById(R.id.headerColumnTypeData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_header_pitur, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pitur, parent, false)
            TableViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            // No binding needed for static header
        } else if (holder is TableViewHolder) {
            val row = tableRows[position - 1] // Adjust for header row
            holder.columnName.text = row.columnName
            holder.columnDimensi.text = row.columnDimensi.toString()
            holder.columnTypeData.text = row.columnTypeData
        }
    }

    override fun getItemCount() = tableRows.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }
}
