package com.example.carshare

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TransactionInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_info)


        var car_name : TextView = findViewById(R.id.textNameCar)
        car_name.text = intent.getSerializableExtra("car_name").toString()
        var car_class : TextView = findViewById(R.id.textClassCar)
        car_class.text = intent.getSerializableExtra("car_class").toString()
        var car_gearbox : TextView = findViewById(R.id.textGearbox)
        car_gearbox.text = intent.getSerializableExtra("car_gearbox").toString()
        var car_fuel : TextView = findViewById(R.id.textFuelTank)
        car_fuel.text= intent.getSerializableExtra("car_fuel").toString()
        var car_address : TextView = findViewById(R.id.textCarAddress)
        car_address.text = intent.getSerializableExtra("car_address").toString()
        var car_rating : TextView = findViewById(R.id.textRating)
        car_rating.text = intent.getSerializableExtra("car_rating").toString()
        var car_passengers : TextView = findViewById(R.id.textNumberOfPersons)
        car_passengers.text = "x" + intent.getSerializableExtra("car_passengers").toString()
        var car_bags : TextView = findViewById(R.id.textNumberOfBags)
        car_bags.text = "x" + intent.getSerializableExtra("car_bags").toString()

        val transaction_start_date : Button = findViewById(R.id.buttonStartBooking)
        transaction_start_date.text = "Start: " + intent.getSerializableExtra("transaction_start_date").toString()

        val transaction_end_date : Button = findViewById(R.id.buttonEndBooking)
        transaction_end_date.text = "End: " + intent.getSerializableExtra("transaction_end_date").toString()

        val transaction_final_price : TextView = findViewById(R.id.textFullPrice)
        transaction_final_price.text = "Total: " + intent.getSerializableExtra("transaction_price").toString() + " PLN"

        val transaction_price_per_day : TextView = findViewById(R.id.textCostPerDay)
        transaction_price_per_day.text = intent.getSerializableExtra("transaction_price_day").toString() + " PLN/day"

        val car_owner : TextView = findViewById(R.id.textCarOwner)
        car_owner.text = intent.getSerializableExtra("car_owner").toString()

        val car_renter : TextView = findViewById(R.id.textCarRenter)
        car_renter.text = intent.getSerializableExtra("car_renter").toString()

    }
}