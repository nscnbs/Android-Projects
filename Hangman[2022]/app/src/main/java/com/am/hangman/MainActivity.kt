package com.am.hangman

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val dictSize = 250
    private val order = ArrayList<Int>()
    private lateinit var word : String
    private lateinit var encodedWord : String
    private var attempt = 1
    private var wordIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createCells()
        randomizeOrder()
        nextWord()
    }

    private fun createCells() {
        var id = 1
        for (i in 'A'..'Z') {
            val cell = Cell(this, i.toString(),id++)
            cell.setOnClickListener{onCharClick(it as Cell)}
            grid.addView(cell)
        }
        val next = Cell(this, "→",id)
        next.setTextColor(Color.BLUE)
        next.setOnClickListener{nextWord()}
        grid.addView(next)
    }

    private fun randomizeOrder() {
        for (i in 1..dictSize) {
            order.add(i)
        }
        order.shuffle()
    }

    private fun nextWord() {
        this.wordIndex++
        if(wordIndex > 250){
            randomizeOrder()
            wordIndex = 1
        }
        this.word = getStringResourceByName("film${order[wordIndex-1]}")
        encodeWord()
        attempt = 1
        refreshWordView()
        refreshGallowsView()
        refreshCells()
    }

    private fun onCharClick(cell : Cell) {
        val char = cell.text[0]
        if(word.contains(char)){
            decryptChar(char)
            cell.setTextColor(Color.GREEN)
        }
        else{
            attempt++
            refreshGallowsView()
            cell.setTextColor(Color.RED)
        }
        cell.isClickable = false
        refreshWordView()
        if(endOfGame()){
            nextWord()
        }
    }

    private fun getStringResourceByName(name: String): String {
        val resId = resources.getIdentifier(name, "string", packageName)
        return getString(resId)
    }

    private fun encodeWord() {
        encodedWord = ""
        for (char in word){
            if(char in 'A'..'Z'){
                encodedWord += "_"
            }
            else{
                encodedWord += char
            }
        }
    }

    private fun decryptChar(char: Char) {
        val newEncodedWord = StringBuilder(encodedWord)
        for ( i in 0..(word.length-1)){
            if (word[i] == char){
                newEncodedWord.setCharAt(i, char)
            }
        }
        encodedWord = newEncodedWord.toString()
    }

    private fun refreshGallowsView() {
        val imgResId = resources.getIdentifier("stage_$attempt", "drawable", packageName)
        gallowsView.setImageResource(imgResId)
    }

    private fun refreshCells() {
        for (id in 1..26){
            val cell = findViewById<Cell>(id)
            cell.isClickable = true
            cell.setTextColor(Color.BLACK)
        }
    }

    private fun refreshWordView() {
        wordView.text = encodedWord
    }

    private fun endOfGame(): Boolean {
        if(attempt == 7 || encodedWord.equals(word)){
            return true
        }
        return false
    }
}