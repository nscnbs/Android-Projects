package com.example.lab3

import android.annotation.SuppressLint
import android.util.Log
import java.time.LocalDate
import java.time.LocalTime

class Event(var name:String,var date: LocalDate,var time: LocalTime) {
    companion object{
        var eventList = mutableListOf<Event>()
        var eventNameList = ArrayList<String>()

        fun eventsForDate(date: LocalDate):MutableList<Event> {
            val events = mutableListOf<Event>()

            for (event:Event in eventList){
                if (event.date == date)
                    events.add(event)
            }
            return events
        }
    }


}