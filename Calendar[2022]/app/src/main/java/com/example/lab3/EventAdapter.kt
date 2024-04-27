package com.example.lab3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.lab3.Event.Companion.eventList

class EventAdapter(context: Context, var events:MutableList<Event>, var weekView:WeekViewActivity) : ArrayAdapter<Event>(context,0,events) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val event: Event? = getItem(position)
        var convView = convertView

        if (convertView == null) {
            convView = LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
        }

        val eventCellTV: TextView? = convView?.findViewById(R.id.eventCellTV)

        val name = event!!.name
        val time = event.time

        convView?.findViewById<Button>(R.id.deleteEventButton)
            ?.setOnClickListener {
                Log.d("deleted", "del")
                for (e in events){
                    if(e.name == name){
                        Log.d("deleted",name)
                        eventList.remove(e)
                        weekView.setEventAdapter()
                    }
                }
            }

        val eventTitle: String = name + " " + CalendarUtils.formattedTime(time)
        eventCellTV?.text = eventTitle
        return convView!!
    }
}