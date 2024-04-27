package com.example.l1z2_kotlin

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var answer: TextView
    lateinit var points: TextView
    lateinit var proby: TextView

    private var value: Int = 0;
    private var liczba = 0
    private var score = 0
    private var prob = 0
    private var max_prob = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.Click)
        answer = findViewById(R.id.answer)
        points = findViewById(R.id.points)
        proby = findViewById(R.id.proby)
        editText = findViewById(R.id.editTextNumber)

        new_roll()
    }

    fun new_roll(){
        points.text = "Punkty: " + score
        prob = 0
        proby.text = "Liczba prob: " + prob
        liczba = Random.nextInt(1, 100)
    }


    fun buttonClick(view: View) {
        if (TextUtils.isEmpty(editText.text.toString())) {
            editText.error = "Pole nie może być puste"
        }
        else if (editText.text.toString().toInt() > 100) {
            editText.error = "Liczba nie może być większa 100"
        }
        else {
            value = editText.text.toString().toInt()
            editText.setText("")
            if (prob == max_prob-1) {
                prob++
                answer.text = "Przekroczona liczba prob: " + prob + "/" + max_prob + "\nNowa gra"
                answer.setTextColor(Color.parseColor("#FF0000"))
                proby.text = "Liczba prob: " + prob
                score--
                new_roll()
            } else {
                if (value == liczba) {
                    score++
                    answer.text = "Gratulacja. Wygrałeś!\n Liczba: " + liczba
                    answer.setTextColor(Color.parseColor("#28b463"))
                    points.text = "Punkty: " + score
                    new_roll()
                } else if (value > liczba) {
                    prob++
                    answer.text = "Liczba większa niż zgadywana"
                    answer.setTextColor(Color.parseColor("#FF0000"))
                    proby.text = "Liczba prob: " + prob
                    Toast.makeText(this, liczba.toString(), Toast.LENGTH_SHORT).show()
                } else if (value < liczba) {
                    prob++
                    answer.text = "Liczba mniejsza niż zgadywana"
                    answer.setTextColor(Color.parseColor("#FF0000"))
                    proby.text = "Liczba prob: " + prob
                    Toast.makeText(this, liczba.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}