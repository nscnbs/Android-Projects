package com.example.carshare.ui.orders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carshare.R
import com.example.carshare.database.CarModel
import com.example.carshare.database.TransactionModel
import java.text.SimpleDateFormat

import java.util.ArrayList

class OrdersAdapter(private val carsList : ArrayList<CarModel>,
                    private val transactionsList : ArrayList<TransactionModel>, private val userID : String?)
    : RecyclerView.Adapter<OrdersAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_sample_transaction,
            parent, false
        )
        return MyViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTransaction = transactionsList[position]
        val currentItem = carsList[position]
        holder.car_name.text = "${currentItem.make} ${currentItem.model} (${currentItem.productionYear})"
        holder.car_class.text = currentItem.type
        holder.car_rating.text = currentItem.rating.toString()
        if (currentTransaction.ownerID == userID) {
            holder.transaction_price.text = "+" + currentTransaction.finalPrice.toString() + " PLN"
            holder.transaction_price.setTextColor(Color.GREEN)
            holder.transaction_day_price.text = "(+" + currentTransaction.perDayPrice.toString() + " PLN/day)"
        }
        else {
            holder.transaction_price.text = "-" + currentTransaction.finalPrice.toString() + " PLN"
            holder.transaction_price.setTextColor(Color.RED)
            holder.transaction_day_price.text = "(-" + currentTransaction.perDayPrice.toString() + " PLN/day)"
        }
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        holder.transaction_date.text = currentTransaction.startDate?.let { dateFormat.format(it) }
        holder.transaction_end_date.text = currentTransaction.endDate?.let { dateFormat.format(it) }
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val car_name: TextView = itemView.findViewById(R.id.textNameCar)
        val car_class: TextView = itemView.findViewById(R.id.textClassCar)
        val car_rating: TextView = itemView.findViewById(R.id.textRating)
        val transaction_price: TextView = itemView.findViewById(R.id.finalPrice)
        val transaction_day_price: TextView = itemView.findViewById(R.id.pricePerDay)
        val transaction_date: TextView = itemView.findViewById(R.id.textDate)
        val transaction_end_date: TextView = itemView.findViewById(R.id.textEndDate)

        init {

            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)

            }

        }

    }
}