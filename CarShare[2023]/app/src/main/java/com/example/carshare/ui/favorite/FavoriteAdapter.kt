package com.example.carshare.ui.favorite


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.carshare.R
import com.example.carshare.database.CarModel

class FavoriteAdapter(private val carsList :ArrayList<CarModel>) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>(){

    private lateinit var mListener : onItemClickListtner

    interface onItemClickListtner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListtner){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_sample_car_info,
            parent,false)
        return MyViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = carsList[position]
        holder.car_name.text = "${currentItem.make} ${currentItem.model} (${currentItem.productionYear})"
        holder.car_class.text = currentItem.type
        holder.car_gearbox.text = currentItem.gearboxType
        holder.car_fuel.text = currentItem.amountOfFuelInKm.toString() + " km"
        holder.car_address.text = currentItem.location
        holder.car_rating.text = currentItem.rating.toString()
        holder.car_passengers.text = currentItem.numberOfSeats.toString() + " passenger(s)"
        holder.car_cost.text = currentItem.price.toString() + " PLN/day"
        holder.car_bags.text = "Small bags:" + currentItem.spaceForBaggage.toString()
    }

    override fun getItemCount(): Int {
        return carsList.size
    }
    class MyViewHolder(itemView :View, listener: onItemClickListtner) : RecyclerView.ViewHolder(itemView)
    {
        val car_name : TextView = itemView.findViewById(R.id.textNameCar)
        val car_class : TextView = itemView.findViewById(R.id.textClassCar)
        val car_gearbox : TextView = itemView.findViewById(R.id.textGearbox)
        val car_fuel : TextView = itemView.findViewById(R.id.textFuelTank)
        val car_address : TextView = itemView.findViewById(R.id.textCarAddress)
        val car_passengers : TextView = itemView.findViewById(R.id.textNumberOfPersons)
        val car_bags : TextView = itemView.findViewById(R.id.textNumberOfBags)
        val car_rating : TextView = itemView.findViewById(R.id.textRating)
        val car_cost : TextView = itemView.findViewById(R.id.textCostPerDay)

        init {

            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)

            }

        }

    }
}