package com.example.lab3

import android.util.Log
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarUtils {

    companion object {
        lateinit  var selectedDate: LocalDate

        fun daysInMonthArray(date: LocalDate): MutableList<LocalDate?> {
            val daysInMonthArray = mutableListOf<LocalDate?>()
            val yearMonth = YearMonth.from(date)

            val daysInMonth = yearMonth.lengthOfMonth()
            val firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1)
            val dayOfWeek = firstOfMonth.dayOfWeek.value
            Log.d("day of week",dayOfWeek.toString())

            for (i in 1..42){
                if (i <= dayOfWeek || i > (daysInMonth + dayOfWeek)){
                    daysInMonthArray.add(null)
                }
                else{
                    val day = i - dayOfWeek
                    Log.d("Day", day.toString())
                    daysInMonthArray.add(LocalDate.of(selectedDate.year, selectedDate.month,
                        i - dayOfWeek))
                }
            }

            return daysInMonthArray
        }

        fun daysInWeekArray(selectedDate: LocalDate): MutableList<LocalDate?> {
            val days = mutableListOf<LocalDate?>()
            var current:LocalDate? = sundayForDate(selectedDate)

            val endDate: LocalDate? = current?.plusWeeks(1)

            while (current?.isBefore(endDate) == true){
                days.add(current)
                current = current.plusDays(1)
            }

            return days
        }

        private fun sundayForDate(current: LocalDate): LocalDate? {
            val oneWeekAgo = current.minusWeeks(1)
            var curr = current

            while (curr.isAfter(oneWeekAgo)){
                if(curr.dayOfWeek == DayOfWeek.SUNDAY){
                    return current
                }
                curr = curr.minusDays(1)
            }

            return null
        }


        fun monthYearFromDate(date:LocalDate) : String{
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
            return date.format(formatter)
        }

        fun formattedDate(date:LocalDate):String{
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            return date.format(formatter)
        }

        fun formattedTime(time:LocalTime):String{
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return time.format(formatter)
        }
    }

}