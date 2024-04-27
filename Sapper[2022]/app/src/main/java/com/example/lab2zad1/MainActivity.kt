package com.example.lab2zad1

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),OnCellClickListener {
    private val TIMER_LENGTH = 99900L
    private val FIELD_WIDTH = 9
    private val BOMBS_NUMBER = 20

    private lateinit var minesweeperBoard: RecyclerView
    private lateinit var mineRecyclerAdapter: CellGridRecyclerAdapter
    private lateinit var game: MinesweeperGame
    private lateinit var smiley: TextView
    private lateinit var timer: TextView
    private lateinit var flag: TextView
    private lateinit var flagsLeft: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var secondsElapsed = 0
    private var timerStarted = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        minesweeperBoard = findViewById(R.id.recyclerGrid)
        minesweeperBoard.layoutManager = GridLayoutManager(this,9)

        timer = findViewById(R.id.timer)
        timerStarted = false
        countDownTimer = object : CountDownTimer(TIMER_LENGTH, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsElapsed++
                timer.text = String.format("%03d", secondsElapsed)
            }

            override fun onFinish() {
                game.outOfTime()
                Toast.makeText(applicationContext, "Game Over: Timer Expired", Toast.LENGTH_SHORT).show()
            }
        }

        flagsLeft = findViewById(R.id.flags_left)

        game = MinesweeperGame(FIELD_WIDTH,BOMBS_NUMBER)
        flagsLeft.text = String.format("%03d", game.numberBombs - game.flagCount)

        mineRecyclerAdapter = CellGridRecyclerAdapter(this,game.mineGrid.cells)
        minesweeperBoard.adapter = mineRecyclerAdapter

        smiley = findViewById(R.id.smiley)
        flag = findViewById(R.id.flag)
        flag.setOnClickListener {
            game.toggleMode()
            val border = GradientDrawable()
            border.color = ColorStateList.valueOf(0xFFFFFFFF.toInt())
            if(game.flagMode) {
                border.setStroke(1,0xFF000000.toInt())

            }
            flag.background = border
        }

    }

    override fun onCellClick(cell: Cell) {
        game.handleCellClick(cell)

        flagsLeft.text = String.format("%03d", game.numberBombs - game.flagCount)

        if (!timerStarted) {
            countDownTimer.start()
            timerStarted = true
        }

        if (game.gameOver) {
            countDownTimer.cancel()
            Toast.makeText(applicationContext, "Game is Over",Toast.LENGTH_SHORT).show()
            game.mineGrid.revealAllBombs()
        } else if(game.isGameWon()) {
            countDownTimer.cancel()
            Toast.makeText(applicationContext, "Game is WON",Toast.LENGTH_SHORT).show()
            game.mineGrid.revealAllBombs()
        }

        mineRecyclerAdapter.cells = game.mineGrid.cells
    }

    fun smileyClicked(view: View) {
        game = MinesweeperGame(FIELD_WIDTH,BOMBS_NUMBER)
        mineRecyclerAdapter.cells = game.mineGrid.cells
        timerStarted = false
        countDownTimer.cancel()
        secondsElapsed = 0
        timer.setText(R.string.timer)
        flagsLeft.text = String.format("%03d", game.numberBombs - game.flagCount)
    }


}