package com.example.lab3

import android.app.AlertDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import java.time.LocalTime
import java.util.Locale

class EventEditActivity : AppCompatActivity() {
    private lateinit var eventDateTv:TextView
    private lateinit var eventTimeTv:TextView
    private lateinit var eventNameET:EditText

    private lateinit var time: LocalTime

    private var hour: Int =0
    private var minute:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_edit)
        initWidgets()
        time = LocalTime.now()
        "Date: ${CalendarUtils.formattedDate(CalendarUtils.selectedDate)}".also { eventDateTv.text = it }
        //"Time: ${CalendarUtils.formattedTime(time)}".also { eventTimeTv.text = it }
        eventTimeTv.text = "Time:"
    }

    private fun initWidgets() {
        eventNameET = findViewById(R.id.eventNameET)
        eventDateTv = findViewById(R.id.eventDateTV)
        eventTimeTv = findViewById(R.id.eventTimeTV)
    }

    fun saveEventAction(view:View){
        val eventName = eventNameET.text.toString()
        Log.d("Event name",eventName)
        val stringTime = String.format("%02d:%02d",hour,minute,0)
        time = LocalTime.parse(stringTime)
        val newEvent = Event(eventName,CalendarUtils.selectedDate,time)
        Event.eventList.add(newEvent)
        finish()
    }

    fun timeChooserClicked(view: View) {
        var onTimeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, selectedHour, selectedMinute ->
            hour = selectedHour
            minute = selectedMinute
            findViewById<Button>(R.id.timeSetButton).text = String.format(Locale.getDefault(),"%02d:%02d",hour,minute)
        }
        val style = AlertDialog.THEME_HOLO_DARK

        val timePickerDialog = TimePickerDialog(this,style,onTimeSetListener,hour,minute,true)

        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }
}