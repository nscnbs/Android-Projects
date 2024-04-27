package com.example.carshare.ui.orders

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carshare.AddCarActivity
import com.example.carshare.MainActivity
import com.example.carshare.R
import com.example.carshare.TransactionInfoActivity
import com.example.carshare.database.CarModel
import com.example.carshare.database.SQLiteHelper
import com.example.carshare.database.TransactionModel
import com.example.carshare.databinding.FragmentOrdersBinding
import java.text.SimpleDateFormat

class OrdersFragment : Fragment() {

    private lateinit var adapter: OrdersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var carsArrayList: ArrayList<CarModel>
    private lateinit var transactionsArrayList: ArrayList<TransactionModel>
    private lateinit var sqliteHelper: SQLiteHelper

    private lateinit var cars_count: TextView

    lateinit var car_name : Array<String>
    lateinit var car_class : Array<String>
    lateinit var car_gearbox : Array<String>
    lateinit var car_fuel : Array<String>
    lateinit var car_address : Array<String>
    lateinit var car_rating : Array<Double>
    lateinit var car_cost : Array<Double>
    lateinit var car_passengers : Array<Byte>
    lateinit var car_bags : Array<Byte>

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:Bundle?
    ): View {

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sqliteHelper = context?.let { SQLiteHelper(it) }!!

        dataInitialize()

        val layoutManager = LinearLayoutManager(context)

        val userPhone = (activity as? MainActivity)?.userPhone
        val userID = sqliteHelper.getUserIdFromPhone(userPhone)

        recyclerView = view.findViewById(R.id.ordersCarsRecycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = OrdersAdapter(carsArrayList, transactionsArrayList, userID)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : OrdersAdapter.onItemClickListener{
            @SuppressLint("SimpleDateFormat")
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, TransactionInfoActivity::class.java)

                intent.putExtra("car_name", carsArrayList.get(position).make
                        + " " + carsArrayList.get(position).model + " (" + carsArrayList.get(position).productionYear + ")")
                intent.putExtra("car_class", carsArrayList.get(position).type)
                intent.putExtra("car_gearbox", carsArrayList.get(position).gearboxType)
                intent.putExtra("car_fuel", "${carsArrayList.get(position).amountOfFuelInKm} km")
                intent.putExtra("car_address", carsArrayList.get(position).location)
                intent.putExtra("car_rating", carsArrayList.get(position).rating)
                intent.putExtra("car_cost", carsArrayList.get(position).price)
                intent.putExtra("car_passengers", carsArrayList.get(position).numberOfSeats)
                intent.putExtra("car_bags", carsArrayList.get(position).spaceForBaggage)

                val dateFormat = SimpleDateFormat("dd-MM-yyyy")

                intent.putExtra("transaction_start_date", transactionsArrayList.get(position).startDate?.let { dateFormat.format(it) })
                intent.putExtra("transaction_end_date", transactionsArrayList.get(position).endDate?.let { dateFormat.format(it) })
                intent.putExtra("transaction_price_day", transactionsArrayList.get(position).perDayPrice.toString())
                intent.putExtra("transaction_price", transactionsArrayList.get(position).finalPrice.toString())

                val owner = sqliteHelper.getUserFromId(transactionsArrayList.get(position).ownerID)
                val renter = sqliteHelper.getUserFromId(transactionsArrayList.get(position).borrowerID)

                intent.putExtra("car_owner", owner.firstname + " " + owner.surname + ", " + owner.phone)
                intent.putExtra("car_renter", renter.firstname + " " + renter.surname + ", " + renter.phone)

                activity?.startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dataInitialize(){
        val userPhone = (activity as? MainActivity)?.userPhone
        transactionsArrayList = sqliteHelper.getAllUserTransactions(userPhone)
        carsArrayList = sqliteHelper.getAllCarsFromTransactions(transactionsArrayList)
        /*

        car_name = arrayOf(
            "Ford Focus",
            "Nisan Leaf",
            "Smart Folio"
        )

        car_class = arrayOf(
            "Small (B)",
            "Medium (C)",
            "Mini (A)"
        )

        car_gearbox = arrayOf(
            "Mechanical",
            "Automatic",
            "Mechanical"
        )

        car_fuel = arrayOf(
            "Full/Full",
            "Full/Full",
            "Empty/Empty"
        )

        car_address = arrayOf(
            "812 Pinnickinnick Street",
            "Sokolovská 790",
            "Jl Moh Yamin 2, Sumatera Utara"
        )

        car_cost = arrayOf(
            110.0,
            150.0,
            100.0
        )

        car_rating = arrayOf(
            4.5,
            4.9,
            3.9
        )

        car_passengers = arrayOf(
            4,
            4,
            4
        )

        car_bags = arrayOf(
            2,
            4,
            2
        )


        for (i in car_name.indices){
            val cars = Cars(car_name[i],car_class[i],car_gearbox[i],car_fuel[i],car_address[i],car_rating[i],car_cost[i],car_passengers[i],car_bags[i])
            carsArrayList.add(cars)
        }

         */
    }
}