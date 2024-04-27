package com.example.lab1kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.lab1_b_kotlin.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var answer: TextView
    lateinit var points: TextView
    private var r1 = 0
    private var liczba1 = 0
    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.Click)
        editText = findViewById(R.id.editTextNumber)
        answer = findViewById(R.id.answer)
        points = findViewById(R.id.points)
        button.setOnClickListener {
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
        score = 0
        answer.text = ""
        new_roll()
    }

    fun new_roll(){
        points.text = "Punkty: " + score
        r1 = Random.nextInt(1, 100)
    }

    fun buttonClick(view: View) {
        if (r1>=liczba1){
            score++
            Toast.makeText(this, "Dobrze!!!", Toast.LENGTH_SHORT).show()
        }
        else{
            score--
            Toast.makeText(this, "Źle!!!", Toast.LENGTH_SHORT).show()
        }
        new_roll()
    }
}