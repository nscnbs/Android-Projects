package com.example.lab1java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int r1;
    private int r2;
    private int score;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = 0;
        roll();
    }

    public void roll(){
        TextView punkty = findViewById(R.id.points);
        TextView Button = findViewById(R.id.Click);

        punkty.setText("Punkty: " + score);
        r1 = rand.nextInt(10);
        r2 = rand.nextInt(10);
        Button.setText("" + r1);
    }

    public void buttonLeft(View view) {
        if(r1>=r2){
            score++;
            Toast.makeText(this, "Dobrze!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            score--;
            Toast.makeText(this, "Źle!!!", Toast.LENGTH_SHORT).show();
        }
        roll();
    }

    public void buttonRight(View view) {
        if(r1<=r2){
            score++;
            Toast.makeText(this, "Dobrze!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            score--;
            Toast.makeText(this, "Źle!!!", Toast.LENGTH_SHORT).show();
        }
        roll();
    }
}