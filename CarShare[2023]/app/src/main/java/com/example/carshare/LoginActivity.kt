package com.example.carshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.carshare.MainActivity
import com.example.carshare.R
import com.example.carshare.database.SQLiteHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var editPassword: EditText
    private lateinit var editPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        sqliteHelper = SQLiteHelper(this)
    }

    /*
    Tries to login user, returns two values:
    'result':
        'LOGGED_IN' - everything worked fine, second value is 'name'
        'WRONG_PASSWORD'
        'WRONG_PHONE' - phone not in database, second value is 'email'
     */
    fun onLoginClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        if(sqliteHelper.isPhoneAlreadyUsed(editPhone.text.toString())) {
            if(sqliteHelper.checkIfUserExists(editPassword.text.toString(), editPhone.text.toString())) {
                val name = sqliteHelper.getUserFirstnameFromPhone(editPhone.text.toString())
                intent.putExtra("result", "LOGGED_IN")
                intent.putExtra("name", sqliteHelper.getUserFirstnameFromPhone(editPhone.text.toString()))
                intent.putExtra("phone", editPhone.text.toString())
                Toast.makeText(this, "Welcome, $name", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            } else {
                intent.putExtra("result", "WRONG_PASSWORD")
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show()
            }
        } else {
            val phone = editPhone.text.toString()
            intent.putExtra("result", "WRONG_PHONE")
            intent.putExtra("email", editPhone.text.toString())
            Toast.makeText(this, "User $phone doesn't exist", Toast.LENGTH_SHORT).show()
        }
        //setResult(RESULT_OK, intent)
        //finish()
        //startActivity(intent)
    }

    fun onRegClick(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun initView() {
        editPassword = findViewById(R.id.editTextPassword)
        editPhone = findViewById(R.id.editTextPhone)
    }
}