package com.example.l1z2_java;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int prob;
    private int liczba;
    private int score;

    private int max_prob = 3;
    private String temp_value;
    private int value;
    Random rand = new Random();
    TextView points, answer, proby;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        points = findViewById(R.id.points);
        answer = findViewById(R.id.answer);
        proby = findViewById(R.id.proby);
        editText = findViewById(R.id.editTextNumber);
        score = 0;

        new_roll();
    }

    public void new_roll(){
        points.setText("Punkty: " + score);
        prob = 0;
        proby.setText("Liczba prob: " + prob);
        liczba = rand.nextInt((100-1)+1)+1;
    }

    public void buttonClick(View view) {
        temp_value = editText.getText().toString().trim();
        editText.setText("");

        if(TextUtils.isEmpty(temp_value)) {
            editText.setError("Pole nie może być puste");
        }
        else if (Integer.parseInt(temp_value) > 100) {
            editText.setError("Liczba nie może być większa 100");
        }
        else {
            value = Integer.parseInt(temp_value);
            if (prob == max_prob-1) {
                prob++;
                answer.setText("Przekroczona liczba prob: " + prob + "/" + max_prob + "\nNowa gra");
                answer.setTextColor(Color.parseColor("#FF0000"));
                proby.setText("Liczba prob: " + prob);
                score--;
                new_roll();
            } else {
                if (value == liczba) {
                    score++;
                    answer.setText("Gratulacja. Wygrałeś!\n Liczba: " + liczba);
                    answer.setTextColor(Color.parseColor("#28b463"));
                    points.setText("Punkty: " + score);
                    new_roll();
                } else if (value > liczba) {
                    prob++;
                    answer.setText("Liczba większa niż zgadywana");
                    answer.setTextColor(Color.parseColor("#FF0000"));
                    proby.setText("Liczba prob: " + prob);
                    Toast.makeText(this, String.valueOf(liczba), Toast.LENGTH_SHORT).show();
                } else {
                    prob++;
                    answer.setText("Liczba mniejsza niż zgadywana");
                    answer.setTextColor(Color.parseColor("#FF0000"));
                    proby.setText("Liczba prob: " + prob);
                    Toast.makeText(this, String.valueOf(liczba), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}