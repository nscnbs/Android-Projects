package com.example.carshare

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.example.carshare.database.CarModel
import com.example.carshare.database.SQLiteHelper
import com.example.carshare.database.TransactionModel
import com.example.carshare.ui.myCars.HomeAdapter
import java.text.SimpleDateFormat
import java.util.Calendar

class CarInfoActivity : AppCompatActivity() {

    private val carModel : CarModel = CarModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_info)


        var car_name : TextView = findViewById(R.id.textNameCar)
        car_name.text = intent.getSerializableExtra("car_name").toString()
        var car_class : TextView = findViewById(R.id.textClassCar)
        car_class.text = intent.getSerializableExtra("car_class").toString()
        var car_gearbox : TextView = findViewById(R.id.textGearbox)
        car_gearbox.text = intent.getSerializableExtra("car_gearbox").toString()
        var car_fuel : TextView = findViewById(R.id.textFuelTank)
        car_fuel.text= intent.getSerializableExtra("car_fuel").toString() + " km"
        var car_address : TextView = findViewById(R.id.textCarAddress)
        car_address.text = intent.getSerializableExtra("car_address").toString()
        var car_rating : TextView = findViewById(R.id.textRating)
        car_rating.text = intent.getSerializableExtra("car_rating").toString()
        var car_cost : TextView = findViewById(R.id.textCostPerDay)
        car_cost.text = intent.getSerializableExtra("car_cost").toString() + " PLN/day"
        var car_passengers : TextView = findViewById(R.id.textNumberOfPersons)
        car_passengers.text = "x" + intent.getSerializableExtra("car_passengers").toString()
        var car_bags : TextView = findViewById(R.id.textNumberOfBags)
        car_bags.text = "x" + intent.getSerializableExtra("car_bags").toString()
        var car_description : TextView = findViewById(R.id.textCarDescription)
        car_description.text = intent.getSerializableExtra("car_description").toString()


        carModel.id = intent.getSerializableExtra("car_id").toString()
        carModel.make = intent.getSerializableExtra("car_make").toString()
        carModel.model = intent.getSerializableExtra("car_model").toString()
        carModel.type = intent.getSerializableExtra("car_class").toString()
        carModel.numberOfSeats = intent.getSerializableExtra("car_passengers").toString().toInt()
        carModel.spaceForBaggage = intent.getSerializableExtra("car_bags").toString().toInt()
        carModel.productionYear = intent.getSerializableExtra("car_production").toString().toInt()
        carModel.gearboxType = intent.getSerializableExtra("car_gearbox").toString()
        carModel.amountOfFuelInKm = intent.getSerializableExtra("car_fuel").toString().toInt()
        carModel.description = intent.getSerializableExtra("car_description").toString()
        carModel.price = intent.getSerializableExtra("car_cost").toString().toDouble()
        carModel.location = intent.getSerializableExtra("car_address").toString()
        carModel.owner = intent.getSerializableExtra("car_owner").toString()
        carModel.rating = intent.getSerializableExtra("car_rating").toString().toDouble()
        carModel.availability = intent.getSerializableExtra("car_availability").toString()
    }

    fun beginTransaction(view: View) {
        val sqLiteHelper = SQLiteHelper(this)

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val dialogView = inflater.inflate(R.layout.transaction_dialog, null)

        val startDateTV = dialogView.findViewById<TextView>(R.id.textStartDate)
        val endDateTV = dialogView.findViewById<TextView>(R.id.textEndDate)

        val buttonStart = dialogView.findViewById<Button>(R.id.buttonPickStartDate)
        val buttonEnd = dialogView.findViewById<Button>(R.id.buttonPickEndDate)

        val startDatePickerDialog = createDatePickerDialog(startDateTV)
        val endDatePickerDialog = createDatePickerDialog(endDateTV)

        buttonStart.setOnClickListener {
            startDatePickerDialog.show()
        }

        buttonEnd.setOnClickListener {
            endDatePickerDialog.show()
        }

        builder.setView(dialogView)

        // Set positive button and its click listener
        builder.setPositiveButton("Submit") { dialog, which ->
            // TODO: Zapewnic ze nie ma blednego inputu
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val receivedStartDate = dateFormat.parse(startDateTV.text.toString())
            val receivedEndDate = dateFormat.parse(endDateTV.text.toString())

            // Calculate the time difference in milliseconds
            val timeDifference = receivedEndDate.time - receivedStartDate.time

            // Convert milliseconds to days
            val daysDifference = (timeDifference / (24 * 60 * 60 * 1000)).toDouble()
            val pricePerDay = intent.getSerializableExtra("car_cost").toString().toDouble()

            val carID = intent.getSerializableExtra("car_id").toString()
            val ownerID = sqLiteHelper.getUserIdFromPhone(intent.getSerializableExtra("car_owner_phone").toString())
            val renterID = sqLiteHelper.getUserIdFromPhone(intent.getSerializableExtra("car_renter_phone").toString())

            val transaction = TransactionModel(
                carID = carID, ownerID = ownerID, borrowerID = renterID,
                perDayPrice = pricePerDay, finalPrice = pricePerDay * daysDifference,
                startDate = receivedStartDate, endDate = receivedEndDate
            )

            sqLiteHelper.insertTransaction(transaction)
            carModel.availability = "No"
            sqLiteHelper.updateCar(carModel)
        }

        // Add a cancel button (optional)
        builder.setNegativeButton("Cancel") { dialog, which ->
            // Handle the cancel button click (if needed)
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun createDatePickerDialog(textViewSelectedDate: TextView): DatePickerDialog {
        val calendar = Calendar.getInstance()

        // Initial values for the DatePickerDialog (current date)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog with a listener for date selection
        return DatePickerDialog(
            this,
            { view: DatePicker, chosenYear: Int, chosenMonth: Int, dayOfMonth: Int ->
                val selectedDate = "$dayOfMonth-${chosenMonth + 1}-$chosenYear"
                textViewSelectedDate.text = selectedDate
            },
            year,
            month,
            day
        )
    }
}