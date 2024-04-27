package com.example.carshare.database

import java.util.Random
import java.util.UUID

class UserModel(
    val id: String = UUID.randomUUID().toString(),
    var firstname: String = "",
    var surname: String = "",
    var password: String="",
    var email: String = "",
    var phone: String = "",
    var country: String = "",
    var address: String = ""
){}