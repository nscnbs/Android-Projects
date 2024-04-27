package com.example.carshare.database

import java.util.Date
import java.util.UUID
class TransactionModel (
    val id: String = UUID.randomUUID().toString(),
    var carID: String = "",
    var ownerID: String = "",
    var borrowerID: String = "",
    var perDayPrice: Double = 0.0,
    var finalPrice: Double = 0.0,
    var startDate : Date? = null,
    var endDate : Date? = null
){}