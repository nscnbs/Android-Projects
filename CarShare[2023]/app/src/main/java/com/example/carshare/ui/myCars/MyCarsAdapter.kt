package com.example.carshare.ui.myCars;

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.carshare.R
import com.example.carshare.database.CarModel

class MyCarsAdapter(private val carsList :ArrayList<CarModel>) : RecyclerView.Adapter<MyCarsAdapter.MyViewHolder>(){

    private lateinit var mListener : onItemClickListtner

    interface onItemClickListtner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListtner){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_sample_my_cars,
            parent,false)
        return MyViewHolder(itemView, mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = carsList[position]
        holder.car_name.text = "${currentItem.make} ${currentItem.model} (${currentItem.productionYear})"
        holder.car_class.text = currentItem.type
        holder.car_rating.text = currentItem.rating.toString()
        holder.car_availability.text = currentItem.availability
        if (holder.car_availability.text == "Yes") {
            holder.car_availability.setTextColor(Color.GREEN)
        }
        else {
            holder.car_availability.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return carsList.size
    }
    class MyViewHolder(itemView :View, listener: onItemClickListtner) : RecyclerView.ViewHolder(itemView)
    {
        val car_name : TextView = itemView.findViewById(R.id.textNameCar)
        val car_class : TextView = itemView.findViewById(R.id.textClassCar)
        val car_rating : TextView = itemView.findViewById(R.id.textRating)
        val car_availability : TextView = itemView.findViewById(R.id.textYesOrNo)

        init {

            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)

            }

        }

    }
}