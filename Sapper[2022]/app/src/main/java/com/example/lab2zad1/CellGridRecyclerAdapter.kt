package com.example.lab2zad1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CellGridRecyclerAdapter(var listener:OnCellClickListener, cells:MutableList<Cell>) : RecyclerView.Adapter<CellGridRecyclerAdapter.TileViewHolder>() {
    var cells = cells
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CellGridRecyclerAdapter.TileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.minesweeper_cell,parent,false)
        return TileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        holder.bind(cells[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    inner class TileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var valTextView: TextView

        init {
            valTextView = itemView.findViewById(R.id.cell_value)
        }

        fun bind(cell:Cell) {
            itemView.setBackgroundColor(Color.LTGRAY)

            itemView.setOnClickListener {


                listener.onCellClick(cell)

            }

            if(cell.isRevealed){
                if(cell.mines == Cell.BOMB) {
                    valTextView.setText(R.string.bomb)
                }else if (cell.mines == Cell.BLANK) {
                    valTextView.text = ""
                    itemView.setBackgroundColor(Color.WHITE)
                }else {
                    valTextView.text = "${cell.mines}"
                    if (cell.mines == 1) {
                        valTextView.setTextColor(Color.BLUE)
                    }else if (cell.mines == 2) {
                        valTextView.setTextColor(Color.GREEN)
                    }else if(cell.mines == 3) {
                        valTextView.setTextColor(Color.RED)
                    }
                }
            } else if (cell.isFlagged) {
                valTextView.setText(R.string.flag)
            }
        }
    }
}