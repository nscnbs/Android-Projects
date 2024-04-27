package com.example.lab3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.CalendarUtils.Companion.daysInWeekArray
import com.example.lab3.CalendarUtils.Companion.monthYearFromDate
import java.time.LocalDate

class WeekViewActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {
    private lateinit var monthTextView: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var eventListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_view)
        initWidgets()
        setWeekView()
    }

    private fun setWeekView() {
        monthTextView.text = monthYearFromDate(CalendarUtils.selectedDate)
        val days:MutableList<LocalDate?> = daysInWeekArray(CalendarUtils.selectedDate)

        val calendarAdapter:CalendarAdapter = CalendarAdapter(days,this)
        val layoutManager:RecyclerView.LayoutManager = GridLayoutManager(applicationContext,7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
        setEventAdapter()
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthTextView = findViewById(R.id.MonthYearTV)
        eventListView = findViewById(R.id.eventListView)
    }

    fun previousWeekClicked(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
        setWeekView()
    }

    fun nextWeekClicked(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
        setWeekView()
    }

    fun newEventAction(view: View) {
        startActivity(Intent(this,EventEditActivity::class.java))
    }

    fun deleteEventAction(view: View){
        Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
    }

    override fun onResume(){
        super.onResume()
        setEventAdapter()
    }

    fun setEventAdapter(){
        val dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate)
        val eventAdapter = EventAdapter(applicationContext,dailyEvents,this)
        eventListView.adapter = eventAdapter
    }

    override fun onItemClick(position: Int, date: LocalDate) {
        CalendarUtils.selectedDate = date
        setWeekView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("date",CalendarUtils.selectedDate)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        CalendarUtils.selectedDate = (savedInstanceState.getSerializable("date") as LocalDate?)!!
        setWeekView()
    }

    fun monthlyAction(view: View) {
        finish()
    }
}
