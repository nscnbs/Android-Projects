package com.example.lab3

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarViewHolder(@NonNull itemView: View,
                         val onItemListener: CalendarAdapter.OnItemListener, val days: MutableList<LocalDate?>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val dayOfMonth: TextView
    val parentView: View

    init {
        itemView.setOnClickListener(this)
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        parentView = itemView.findViewById(R.id.parentView)
    }

    @Override
    override fun onClick(p0: View?) {
        days[adapterPosition]?.let { onItemListener.onItemClick(adapterPosition, it) }
    }
}