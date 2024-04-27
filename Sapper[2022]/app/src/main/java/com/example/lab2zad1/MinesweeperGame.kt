package com.example.lab2zad1

class MinesweeperGame(var size: Int,var numberBombs: Int) {
    var mineGrid = CellGrid(size)
    var clearMode = true
    var gameOver = false
    var timeExpired = false
    var flagCount = 0
    var flagMode = false

    init {
        mineGrid.generateGrid(numberBombs)
    }

    fun handleCellClick(cell:Cell) {
        if(!gameOver && !isGameWon() && !timeExpired) {
            if(clearMode) {
                clear(cell)
            } else if(flagMode) {
                flag(cell)
            }
        }
    }

    private fun clear(cell:Cell) {
        val index = mineGrid.cells.indexOf(cell)
        mineGrid.cells[index].isRevealed = true

        if(cell.mines == Cell.BOMB) {
            gameOver = true
        } else if(cell.mines == Cell.BLANK) {
            val toClear = mutableListOf<Cell>()
            val forSurroundingCheck = mutableListOf<Cell>()

            forSurroundingCheck.add(cell)

            while (forSurroundingCheck.size > 0) {
                val c = forSurroundingCheck[0]
                val cellIndex = mineGrid.cells.indexOf(c)
                val cellPos = mineGrid.toXY(cellIndex)
                for (surrounding in mineGrid.surroundingCells(cellPos[0],cellPos[1])) {
                    if (surrounding.mines == Cell.BLANK) {
                        if(!toClear.contains(surrounding)) {
                            if(!forSurroundingCheck.contains(surrounding)) {
                                forSurroundingCheck.add(surrounding)
                            }
                        }
                    } else {
                        if(!toClear.contains(surrounding)) {
                            toClear.add(surrounding)
                        }
                    }
                }
                forSurroundingCheck.remove(c)
                toClear.add(c)
            }

            for (c in toClear) {
                c.isRevealed = true
            }
        }
    }

    fun isGameWon() : Boolean {
        var numbersUnrevealed = 0
        for(c in mineGrid.cells) {
            if(c.mines != Cell.BOMB && c.mines != Cell.BLANK && !c.isRevealed) {
                numbersUnrevealed++
            }
        }

        return numbersUnrevealed == 0
    }

    fun toggleMode() {
        clearMode = !clearMode
        flagMode = !flagMode
    }

    fun flag(cell: Cell) {
        if(!cell.isRevealed) {
            cell.isFlagged = !cell.isFlagged
            var count = 0
            for(c in mineGrid.cells) {
                if(c.isFlagged) {
                    count++
                }
            }
            flagCount = count
        }
    }

    fun outOfTime() {
        timeExpired = true
    }
}