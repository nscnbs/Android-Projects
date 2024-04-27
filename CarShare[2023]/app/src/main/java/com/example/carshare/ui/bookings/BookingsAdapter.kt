package com.example.carshare.ui.bookings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carshare.R
import com.example.carshare.database.CarModel

import java.util.ArrayList

class BookingsAdapter(private val carsList : ArrayList<CarModel>) : RecyclerView.Adapter<BookingsAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_sample_my_cars,
            parent, false
        )
        return MyViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = carsList[position]
        holder.car_name.text = "${currentItem.make} ${currentItem.model} (${currentItem.productionYear})"
        holder.car_class.text = currentItem.type
        holder.car_rating.text = currentItem.rating.toString()
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val car_name: TextView = itemView.findViewById(R.id.textNameCar)
        val car_class: TextView = itemView.findViewById(R.id.textClassCar)
        val car_rating: TextView = itemView.findViewById(R.id.textRating)

        init {

            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)

            }

        }

    }
}
