package com.example.carshare.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 6
        private const val DATABASE_NAME = "car-share-updated6.db"
        private const val TBL_USER = "tbl_user"
        private const val ID = "id"
        private const val FIRSTNAME = "firstname"
        private const val SURNAME = "surname"
        private const val PASSWORD = "password"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        private const val COUNTRY = "country"
        private const val ADDRESS = "address"

        private const val TBL_CARS = "tbl_car"
        private const val MAKE = "make"
        private const val MODEL = "model"
        private const val TYPE = "type"
        private const val NO_OF_SEATS = "seats"
        private const val NO_OF_BAGGAGE = "baggage"
        private const val PRODUCTION_YEAR = "productionYear"
        private const val GEARBOX_TYPE = "gearboxType"
        private const val FUEL = "fuel"
        private const val DESCRIPTION = "description"
        private const val PRICE = "price"
        private const val LOCATION = "location"
        private const val OWNER = "owner"
        private const val RATING = "rating"
        private const val AVAILABILITY = "availability"

        private const val TBL_TRANSACTIONS = "tbl_transaction"
        private const val CAR_ID = "car_id"
        private const val BORROWER_ID = "borrower_id"
        private const val PRICE_PER_DAY = "price_day"
        private const val START_DATE = "start_date"
        private const val END_DATE = "end_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblUser = ("CREATE TABLE " + TBL_USER + "("
                + ID + " TEXT PRIMARY KEY," + FIRSTNAME + " TEXT," +
                SURNAME + " TEXT," + PASSWORD + " TEXT," + EMAIL + " TEXT," +
                COUNTRY + " TEXT," + ADDRESS + " TEXT," + PHONE + " TEXT" + ")")

        val createTblCar = ("CREATE TABLE " + TBL_CARS + "("
                + ID + " TEXT PRIMARY KEY," + MAKE + " TEXT," +
                MODEL + " TEXT," + TYPE + " TEXT," + NO_OF_SEATS + " TEXT,"
                + NO_OF_BAGGAGE + " TEXT," + PRODUCTION_YEAR + " TEXT," +
                GEARBOX_TYPE + " TEXT," + FUEL + " TEXT," + DESCRIPTION + " TEXT,"
                + PRICE + " TEXT," + LOCATION + " TEXT," + OWNER + " TEXT,"
                + RATING + " TEXT," + AVAILABILITY + " TEXT" + ")")

        val createTblTransaction = ("CREATE TABLE " + TBL_TRANSACTIONS + "("
                + ID + " TEXT PRIMARY KEY," + CAR_ID + " TEXT," + OWNER + " TEXT,"
                + BORROWER_ID + " TEXT," + PRICE + " TEXT," + PRICE_PER_DAY + " TEXT,"
                + START_DATE + " TEXT," + END_DATE + " TEXT" + ")")

        db?.execSQL(createTblUser)
        db?.execSQL(createTblCar)
        db?.execSQL(createTblTransaction)
        Log.i("DATABASE", "CREATED OR UPDATED DATABASE")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_CARS")
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_TRANSACTIONS")
        onCreate(db)
    }

    //-------------------------------USER SECTION--------------------------------------------------------

    //returns -1 if failed and 0 if worked
    fun insertUser(std: UserModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(FIRSTNAME, std.firstname)
        contentValues.put(SURNAME, std.surname)
        contentValues.put(PASSWORD, std.password)
        contentValues.put(EMAIL, std.email)
        contentValues.put(PHONE, std.phone)
        contentValues.put(COUNTRY, std.country)
        contentValues.put(ADDRESS, std.address)

        val success = db.insert(TBL_USER, null, contentValues)
        db.close()
        return success
    }

    //returns true if phone in database, false if not
    //parameter: phone number as a string
    @SuppressLint("Recycle")
    fun isPhoneAlreadyUsed(newPhone: String): Boolean {
        val selectQuery = "SELECT * FROM $TBL_USER WHERE phone=\"$newPhone\" "
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return false
        }
        val count = cursor.count
        val test = count >= 1
        cursor.close()
        Log.e("PHONE COUNTER", "$count")
        Log.e("RETURNING", "$test")
        return count >= 1
    }

    //return true if email in database, false if not
    //parameter: email as a string
    fun isEmailAlreadyUsed(newEmail: String): Boolean {
        val selectQuery = "SELECT * FROM $TBL_USER WHERE email=\"$newEmail\" "
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return false
        }
        val count = cursor.count
        cursor.close()
        return count >= 1
    }

    //check if user with given password and user exists
    //returns true or false
    fun checkIfUserExists(password: String, phone: String): Boolean {
        val selectQuery = "SELECT * FROM $TBL_USER WHERE phone=\"$phone\" AND password=\"$password\" "
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return false
        }
        val count = cursor.count
        cursor.close()
        return count >= 1
    }

    //parameter: user email
    //output: user firstname
    //if user with given email not found returns "error"
    @SuppressLint("Range")
    fun getUserFirstnameFromPhone(phone: String): String {
        val selectQuery = "SELECT firstname FROM $TBL_USER WHERE phone=\"$phone\" "
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return "error"
        }
        cursor!!.moveToFirst()
        val firstname = cursor.getString(cursor.getColumnIndex("firstname"))
        cursor.close()
        return firstname
    }

    @SuppressLint("Range")
    fun getUserIdFromPhone(phone: String?): String {
        val selectQuery = "SELECT id FROM $TBL_USER WHERE phone=\"$phone\" "
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return "error"
        }
        cursor!!.moveToFirst()
        val id = cursor.getString(cursor.getColumnIndex("id"))
        cursor.close()
        return id
    }

    @SuppressLint("Range")
    fun getUserFromId(userID : String): UserModel {
        val user : UserModel
        val selectQuery = "SELECT * FROM $TBL_USER WHERE id=\"$userID\" "

        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        cursor!!.moveToFirst()
        val firstname = cursor.getString(cursor.getColumnIndex("firstname"))
        val surname = cursor.getString(cursor.getColumnIndex("surname"))
        val password = cursor.getString(cursor.getColumnIndex("password"))
        val email = cursor.getString(cursor.getColumnIndex("email"))
        val phone = cursor.getString(cursor.getColumnIndex("phone"))
        val country = cursor.getString(cursor.getColumnIndex("country"))
        val address = cursor.getString(cursor.getColumnIndex("address"))
        cursor.close()

        user = UserModel(userID, firstname, surname, password, email, phone, country, address)

        return user
    }

    // returns all users in database as list
    @SuppressLint("Range")
    fun getAllUser(): ArrayList<UserModel> {
        val userList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USER"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }

        var id: String
        var firstname: String
        var surname: String
        var email: String
        var phone: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex("id"))
                firstname = cursor.getString(cursor.getColumnIndex("firstname"))
                surname = cursor.getString(cursor.getColumnIndex("surname"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))

                val user = UserModel(id = id, firstname = firstname, surname = surname, email = email, phone = phone)
                userList.add(user)
            } while(cursor.moveToNext())
        }

        cursor.close()
        return userList
    }

    fun printAllUser() {
        val allUsers = getAllUser()
        for (user in allUsers) {
            Log.i("UŻYTKOWNIK", "${user.id} ${user.email} ${user.firstname} ${user.surname} ${user.phone}")
        }
    }

    //-------------------------------CAR SECTION--------------------------------------------------------
    //returns -1 if failed and 0 if worked
    fun insertCar(std: CarModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(MAKE, std.make)
        contentValues.put(MODEL, std.model)
        contentValues.put(TYPE, std.type)
        contentValues.put(NO_OF_SEATS, std.numberOfSeats)
        contentValues.put(NO_OF_BAGGAGE, std.spaceForBaggage)
        contentValues.put(PRODUCTION_YEAR, std.productionYear)
        contentValues.put(GEARBOX_TYPE, std.gearboxType)
        contentValues.put(FUEL, std.amountOfFuelInKm)
        contentValues.put(DESCRIPTION, std.description)
        contentValues.put(PRICE, std.price)
        contentValues.put(LOCATION, std.location)
        contentValues.put(OWNER, std.owner)
        contentValues.put(RATING, std.rating)
        contentValues.put(AVAILABILITY, std.availability)

        val success = db.insert(TBL_CARS, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun updateCar(updatedCar: CarModel) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(MAKE, updatedCar.make)
        contentValues.put(MODEL, updatedCar.model)
        contentValues.put(TYPE, updatedCar.type)
        contentValues.put(NO_OF_SEATS, updatedCar.numberOfSeats)
        contentValues.put(NO_OF_BAGGAGE, updatedCar.spaceForBaggage)
        contentValues.put(PRODUCTION_YEAR, updatedCar.productionYear)
        contentValues.put(GEARBOX_TYPE, updatedCar.gearboxType)
        contentValues.put(FUEL, updatedCar.amountOfFuelInKm)
        contentValues.put(DESCRIPTION, updatedCar.description)
        contentValues.put(PRICE, updatedCar.price)
        contentValues.put(LOCATION, updatedCar.location)
        contentValues.put(AVAILABILITY, updatedCar.availability)

        val rows_affected = db.update(TBL_CARS, contentValues, "id=?", arrayOf(updatedCar.id))

        db.close()
        return rows_affected
    }

    @SuppressLint("Range")
    fun getCarById(carID: String) : CarModel {
        val selectQuery = "SELECT * FROM $TBL_CARS WHERE id=\"$carID\" "
        val db = this.readableDatabase

        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        cursor!!.moveToFirst()

        val id = cursor.getString(cursor.getColumnIndex("id"))
        val make = cursor.getString(cursor.getColumnIndex("make"))
        val model = cursor.getString(cursor.getColumnIndex("model"))
        val type = cursor.getString(cursor.getColumnIndex("type"))
        val numberOfSeats = cursor.getString(cursor.getColumnIndex("seats")).toInt()
        val spaceForBaggage = cursor.getString(cursor.getColumnIndex("baggage")).toInt()
        val productionYear = cursor.getString(cursor.getColumnIndex("productionYear")).toInt()
        val gearBoxType = cursor.getString(cursor.getColumnIndex("gearboxType"))
        val amountOfFuel = cursor.getString(cursor.getColumnIndex("fuel")).toInt()
        val description = cursor.getString(cursor.getColumnIndex("description"))
        val price = cursor.getString(cursor.getColumnIndex("price")).toDouble()
        val location = cursor.getString(cursor.getColumnIndex("location"))
        val owner = cursor.getString(cursor.getColumnIndex("owner"))
        val rating = cursor.getString(cursor.getColumnIndex("rating")).toDouble()
        val availability = cursor.getString(cursor.getColumnIndex("availability"))

        val receivedCar = CarModel(id, make, model, type, numberOfSeats, spaceForBaggage,
            productionYear, gearBoxType, amountOfFuel, description,
            price, location, owner, rating, availability)

        cursor.close()
        return receivedCar
    }

    @SuppressLint("Range")
    fun getAllAvailableCars() : ArrayList<CarModel> {
        val selectQuery = "SELECT * FROM $TBL_CARS WHERE $AVAILABILITY='Yes'"
        return getMultipleCarsWithQuery(selectQuery)
    }

    @SuppressLint("Range")
    fun getAllCars() : ArrayList<CarModel> {
        val selectQuery = "SELECT * FROM $TBL_CARS"
        return getMultipleCarsWithQuery(selectQuery)
    }

    @SuppressLint("Range")
    fun getUserCarsFromPhone(phone : String?) : ArrayList<CarModel> {
        val selectQuery = "SELECT * FROM $TBL_CARS WHERE owner=\"$phone\" "
        return getMultipleCarsWithQuery(selectQuery)
    }

    @SuppressLint("Range")
    fun getAllCarsFromTransactions(transactions : ArrayList<TransactionModel>) : ArrayList<CarModel> {
        val carList: ArrayList<CarModel> = ArrayList()

        for (transaction in transactions) {
            carList.add(getCarById(transaction.carID))
        }

        return carList
    }

    @SuppressLint("Range")
    fun getMultipleCarsWithQuery(selectQuery : String) : ArrayList<CarModel> {
        val carList: ArrayList<CarModel> = ArrayList()
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }

        var id: String
        var make: String
        var model: String
        var type: String
        var numberOfSeats: Int
        var spaceForBaggage: Int
        var productionYear: Int
        var gearBoxType: String
        var amountOfFuel : Int
        var description : String
        var price : Double
        var location : String
        var owner : String
        var rating : Double
        var availability : String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex("id"))
                make = cursor.getString(cursor.getColumnIndex("make"))
                model = cursor.getString(cursor.getColumnIndex("model"))
                type = cursor.getString(cursor.getColumnIndex("type"))
                numberOfSeats = cursor.getString(cursor.getColumnIndex("seats")).toInt()
                spaceForBaggage = cursor.getString(cursor.getColumnIndex("baggage")).toInt()
                productionYear = cursor.getString(cursor.getColumnIndex("productionYear")).toInt()
                gearBoxType = cursor.getString(cursor.getColumnIndex("gearboxType"))
                amountOfFuel = cursor.getString(cursor.getColumnIndex("fuel")).toInt()
                description = cursor.getString(cursor.getColumnIndex("description"))
                price = cursor.getString(cursor.getColumnIndex("price")).toDouble()
                location = cursor.getString(cursor.getColumnIndex("location"))
                owner = cursor.getString(cursor.getColumnIndex("owner"))
                rating = cursor.getString(cursor.getColumnIndex("rating")).toDouble()
                availability = cursor.getString(cursor.getColumnIndex("availability"))

                val car = CarModel(id, make, model, type, numberOfSeats, spaceForBaggage,
                    productionYear, gearBoxType, amountOfFuel, description,
                    price, location, owner, rating, availability)
                carList.add(car)
            } while(cursor.moveToNext())
        }

        cursor.close()
        return carList
    }

    //-------------------------------TRANSACTIONS SECTION--------------------------------------------------------
    @SuppressLint("Range", "SimpleDateFormat")
    fun insertTransaction(transaction : TransactionModel) : Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, transaction.id)
        contentValues.put(CAR_ID, transaction.carID)
        contentValues.put(OWNER, transaction.ownerID)
        contentValues.put(BORROWER_ID, transaction.borrowerID)
        contentValues.put(PRICE, transaction.finalPrice)
        contentValues.put(PRICE_PER_DAY, transaction.perDayPrice)

        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        contentValues.put(START_DATE, transaction.startDate?.let { dateFormat.format(it) })
        contentValues.put(END_DATE, transaction.endDate?.let { dateFormat.format(it) })

        val success = db.insert(TBL_TRANSACTIONS, null, contentValues)
        db.close()
        return success
    }



    @SuppressLint("Range")
    fun getAllUserTransactions(phone : String?) : ArrayList<TransactionModel> {
        val transactionList: ArrayList<TransactionModel> = ArrayList()
        val userId = getUserIdFromPhone(phone)

        // Get all transaction that the user was involved in
        val selectQuery = "SELECT * FROM $TBL_TRANSACTIONS WHERE owner=\"$userId\" OR borrower_id=\"$userId\""
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }

        var id: String
        var carID : String
        var ownerID : String
        var borrowerID : String
        var price : Double
        var pricePerDay : Double
        var start_date : Date?
        var end_date : Date?
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex("id"))
                carID = cursor.getString(cursor.getColumnIndex("car_id"))
                ownerID = cursor.getString(cursor.getColumnIndex("owner"))
                borrowerID = cursor.getString(cursor.getColumnIndex("borrower_id"))
                price = cursor.getString(cursor.getColumnIndex("price")).toDouble()
                pricePerDay = cursor.getString(cursor.getColumnIndex("price_day")).toDouble()
                start_date = dateFormat.parse(cursor.getString(cursor.getColumnIndex("start_date")))
                end_date = dateFormat.parse(cursor.getString(cursor.getColumnIndex("end_date")))

                val transaction = TransactionModel(id, carID, ownerID, borrowerID, pricePerDay, price, start_date, end_date)
                transactionList.add(transaction)
            } while(cursor.moveToNext())
        }

        cursor.close()
        return transactionList
    }
}