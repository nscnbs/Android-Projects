package com.example.carshare.database

import java.util.UUID
class CarModel(
    var id: String = UUID.randomUUID().toString(),
    var make: String = "",
    var model: String = "",
    var type: String = "",
    var numberOfSeats: Int = 0,
    var spaceForBaggage: Int = 0,
    var productionYear: Int = 0,
    var gearboxType: String = "",//Transmission? = null,
    var amountOfFuelInKm: Int = 0,
    var description: String = "",
    var price: Double = 0.0,
    var location: String = "",
    var owner: String = "",
    var rating : Double = 0.0,
    var availability : String = ""
){}