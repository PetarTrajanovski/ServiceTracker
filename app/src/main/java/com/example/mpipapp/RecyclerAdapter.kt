package com.example.mpipapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val customerList : ArrayList<Customer>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_items ,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = customerList[position]

        holder.name.text = currentitem.name
        holder.phone.text = currentitem.phone
        holder.address.text = currentitem.address
        holder.model.text = currentitem.model
        holder.brand.text = currentitem.brand
        holder.date.text = currentitem.date
    }

    override fun getItemCount(): Int {
        return customerList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.namePlaceHolder)
        val phone : TextView = itemView.findViewById(R.id.phonePlaceHolder)
        val address : TextView = itemView.findViewById(R.id.addressPlaceHolder)
        val brand : TextView = itemView.findViewById(R.id.brandPlaceHolder)
        val model : TextView = itemView.findViewById(R.id.modelPlaceHolder)
        val date : TextView = itemView.findViewById(R.id.datePlaceHolder)
    }
}