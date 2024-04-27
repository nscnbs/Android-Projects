package com.example.lab2zad1

import kotlin.random.Random

class CellGrid(var size: Int) {
     val cells = mutableListOf<Cell>()

    init {
        for (i in 0 until (size)*(size))
            cells.add(Cell(Cell.BLANK))
    }

    fun toIndex(x:Int, y:Int): Int {
        return x + (y*size)
    }

    fun toXY(index:Int) : IntArray {
        val y = index / size
        val x = index - (y*size)
        return intArrayOf(x,y)
    }

    fun cellAt(x:Int,y: Int) : Cell? {
        if(x<0 || x>=size || y<0 || y>=size) {
            return null
        }
        return cells[x+(y*size)]
    }

    fun surroundingCells(x:Int,y: Int) : MutableList<Cell> {
        val list = mutableListOf<Cell>()

        val cellsForCheck = mutableListOf<Cell?>()
        cellsForCheck.add(cellAt(x-1,y))
        cellsForCheck.add(cellAt(x+1,y))
        cellsForCheck.add(cellAt(x-1, y-1))
        cellsForCheck.add(cellAt(x, y-1))
        cellsForCheck.add(cellAt(x+1, y-1))
        cellsForCheck.add(cellAt(x-1, y+1))
        cellsForCheck.add(cellAt(x, y+1))
        cellsForCheck.add(cellAt(x+1, y+1))

        for (cell in cellsForCheck) {
            if(cell != null) {
                list.add(cell)
            }
        }

        return list
    }

    fun revealAllBombs() {
        for (c in cells) {
            if(c.mines == Cell.BOMB) {
                c.isRevealed = true
            }
        }
    }

    fun generateGrid(numberBombs:Int) {
        var bombsPlaced = 0
        while (bombsPlaced < numberBombs) {
            val x = Random.nextInt(size)
            val y = Random.nextInt(size)

            val index = toIndex(x,y)
            if(cells[index].mines == Cell.BLANK){
                cells[index] = Cell(Cell.BOMB)
                bombsPlaced++
            }
        }

        for (x in 0 until size) {
            for (y in 0 until size) {
                if(cellAt(x,y)?.mines != Cell.BOMB) {
                    val surroundingCells = surroundingCells(x,y)
                    var countBombs = 0
                    for(cell in surroundingCells) {
                        if(cell.mines == Cell.BOMB) {
                            countBombs++
                        }
                    }
                    if(countBombs>0) {
                        cells[x+(y*size)] = Cell(countBombs)
                    }
                }
            }
        }
    }
}