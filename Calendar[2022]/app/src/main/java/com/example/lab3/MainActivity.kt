package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class MainActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {
    private lateinit var monthYearText:TextView
    private lateinit var calendarRecyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()

        CalendarUtils.selectedDate = LocalDate.now()

        Log.d("selected date",CalendarUtils.selectedDate.toString())
        setMonthView()
    }


    private fun initWidgets(){
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.MonthYearTV)
    }

    private fun setMonthView() {
        monthYearText.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        val daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager:RecyclerView.LayoutManager = GridLayoutManager(applicationContext,7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }


    fun previousMonthClicked(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonthClicked(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1)
        setMonthView()
    }


    override fun onItemClick(position: Int, date: LocalDate) {
        if(date != null){
            CalendarUtils.selectedDate = date
            setMonthView()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("date",CalendarUtils.selectedDate)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        CalendarUtils.selectedDate = (savedInstanceState.getSerializable("date") as LocalDate?)!!
        setMonthView()
    }

    fun weeklyAction(view: View) {
        startActivity(Intent(this,WeekViewActivity::class.java))
    }

}