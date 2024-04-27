package com.example.carshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import com.example.carshare.database.CarModel
import com.example.carshare.database.SQLiteHelper
import com.example.carshare.MainActivity

class AddCarActivity : AppCompatActivity() {
    private lateinit var sqliteHelper: SQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        sqliteHelper = SQLiteHelper(this)

        if (intent.getSerializableExtra("car_make")==null){

        }else{

            val car_name : TextView = findViewById(R.id.editTextCarName)
            car_name.text = intent.getSerializableExtra("car_make").toString()

            val car_model : TextView = findViewById(R.id.editTextCarModel)
            car_model.text = intent.getSerializableExtra("car_model").toString()

            val car_class : Spinner = findViewById(R.id.spinnerCarClass)
            car_class.setSelection(resources.getStringArray(R.array.carClass).indexOf(intent.getSerializableExtra("car_type").toString()))

            val car_gearbox : Spinner = findViewById(R.id.spinnerCarGearbox)
            car_gearbox.setSelection(resources.getStringArray(R.array.carGearbox).indexOf(intent.getSerializableExtra("car_gearbox").toString()))

            val car_fuel : TextView = findViewById(R.id.editTextFuelRange)
            car_fuel.text = intent.getSerializableExtra("car_fuel").toString()

            val car_address : TextView = findViewById(R.id.editTextCarAddress)
            car_address.text = intent.getSerializableExtra("car_address").toString()

            val car_cost : TextView = findViewById(R.id.editTextCarPrice)
            car_cost.text = intent.getSerializableExtra("car_price").toString()
            val car_passengers : TextView = findViewById(R.id.editTextNumberOfPeople)
            car_passengers.text = intent.getSerializableExtra("car_passengers").toString()
            val car_bags : TextView = findViewById(R.id.editTextNumberOfBags)
            car_bags.text =intent.getSerializableExtra("car_bags").toString()

            val car_production : TextView = findViewById(R.id.editTextProductionYear)
            car_production.text = intent.getSerializableExtra("car_year").toString()

            val car_description : TextView = findViewById(R.id.editTextCarDescription)
            car_description.text = intent.getSerializableExtra("car_description").toString()

            val car_availability : Spinner = findViewById(R.id.spinnerAvailability)
            car_availability.setSelection(resources.getStringArray(R.array.carAvailability).indexOf(intent.getSerializableExtra("car_availability").toString()))
        }
    }

    fun onContinueClick(view: View) {
        val createdCar = CarModel()

        val carName : TextView = findViewById(R.id.editTextCarName)
        createdCar.make = carName.text.toString()
        val carModel : TextView = findViewById(R.id.editTextCarModel)
        createdCar.model = carModel.text.toString()
        val carType : Spinner = findViewById(R.id.spinnerCarClass)
        createdCar.type = carType.selectedItem.toString()
        val carSeats : TextView = findViewById(R.id.editTextNumberOfPeople)
        createdCar.numberOfSeats = carSeats.text.toString().toInt()
        val carBaggage : TextView = findViewById(R.id.editTextNumberOfBags)
        createdCar.spaceForBaggage = carBaggage.text.toString().toInt()
        val carProduction : TextView = findViewById(R.id.editTextProductionYear)
        createdCar.productionYear = carProduction.text.toString().toInt()
        val carGearbox : Spinner = findViewById(R.id.spinnerCarGearbox)
        createdCar.gearboxType = carGearbox.selectedItem.toString()
        val carFuel : TextView = findViewById(R.id.editTextFuelRange)
        createdCar.amountOfFuelInKm = carFuel.text.toString().toInt()
        val carDescription : TextView = findViewById(R.id.editTextCarDescription)
        createdCar.description = carDescription.text.toString()
        val carPrice : TextView = findViewById(R.id.editTextCarPrice)
        createdCar.price = carPrice.text.toString().toDouble()
        val carAddress : TextView = findViewById(R.id.editTextCarAddress)
        createdCar.location = carAddress.text.toString()
        val carAvailability : Spinner = findViewById(R.id.spinnerAvailability)
        createdCar.availability = carAvailability.selectedItem.toString()

        createdCar.owner = intent.getStringExtra("phone").toString()

        // Checking if the car is being updated or created
        if (intent.getSerializableExtra("car_id") == null) {
            sqliteHelper.insertCar(createdCar)
        }
        else {
            createdCar.id = intent.getSerializableExtra("car_id").toString()
            sqliteHelper.updateCar(createdCar)
        }


        finish()
    }
}