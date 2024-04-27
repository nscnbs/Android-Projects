package com.example.carshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.carshare.R
import com.example.carshare.database.SQLiteHelper
import com.example.carshare.database.UserModel
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {
    private lateinit var editFirstname: EditText
    private lateinit var editSurname: EditText
    private lateinit var editPassword: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var editCountry: EditText
    private lateinit var editAddress: EditText
    private lateinit var addButton: Button

    private lateinit var sqliteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initView()
        sqliteHelper = SQLiteHelper(this)

        addButton.setOnClickListener { addUser() }
    }

    fun onLoginClick(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    //Adds new user to database, create toast when there is any error with user data
    private fun addUser() {
        val firstname = editFirstname.text.toString()
        val surname = editSurname.text.toString()
        val password = editPassword.text.toString()
        val email = editEmail.text.toString()
        val phone = editPhone.text.toString()
        val address = editAddress.text.toString()
        val country = editCountry.text.toString()

        if(firstname.isEmpty() || surname.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show()
        } else {
            if(sqliteHelper.isPhoneAlreadyUsed(phone)) {
                Toast.makeText(this, "Phone already in use", Toast.LENGTH_SHORT).show()
                return
            }
            if(sqliteHelper.isEmailAlreadyUsed(email)) {
                Toast.makeText(this, "Email already in use", Toast.LENGTH_SHORT).show()
                return
            }
            if(!isEmailValid(email)) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show()
                return
            }
            val user = UserModel(firstname = firstname, surname = surname, password = password, email = email, phone = phone, address = address, country = country)
            val status = sqliteHelper.insertUser(user)
            if (status > -1) {
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Registration failed, please contact our support", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    //Checks if email fits in pattern [letters,numbers]@[letters].[letters]
    //returns true / false
    fun isEmailValid(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return emailPattern.matcher(email).matches()
    }

    //Clears input fields
    private fun clearEditText() {
        editFirstname.setText("")
        editSurname.setText("")
        editPassword.setText("")
        editEmail.setText("")
        editPhone.setText("")
        editAddress.setText("")
        editCountry.setText("")
    }

    private fun initView() {
        editFirstname = findViewById(R.id.editTextName)
        editSurname = findViewById(R.id.editTextSurname)
        editPassword = findViewById(R.id.editTextPassword)
        editEmail = findViewById(R.id.editTextEmail)
        editPhone = findViewById(R.id.editTextPhone)
        editCountry = findViewById(R.id.editTextCountry)
        editAddress = findViewById(R.id.editTextAddress)
        addButton = findViewById(R.id.buttonSingUp)
    }
}