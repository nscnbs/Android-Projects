package com.example.lab3

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarAdapter(
    val days: MutableList<LocalDate?>,
    val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarViewHolder>() {

    @Override
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.calendar_cell,parent,false)
        val layoutParams :ViewGroup.LayoutParams = view.layoutParams
        if(days.size > 15){
            layoutParams.height = (parent.height * 0.126666666).toInt()
        }else{
            //week view
            layoutParams.height = parent.height.toInt()
        }

        Log.d("height",layoutParams.height.toString())
        //layoutParams.height = (parent.height * 0.16).toInt()
        return  CalendarViewHolder(view, onItemListener, days)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date: LocalDate? = days[position]
        if(date == null){
            holder.dayOfMonth.text = ""
        }else{
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            if(date.equals(CalendarUtils.selectedDate)){
                holder.parentView.setBackgroundColor(Color.LTGRAY)
            }
        }
    }

    interface OnItemListener{
        fun onItemClick(position:Int,date: LocalDate)
    }
}