package com.example.lab2zad1

class Cell(var mines:Int) {

    //mines is a public variable

    var isRevealed = false

    var isFlagged = false

    companion object {
        const val BLANK = 0
        const val BOMB = -1
    }

}