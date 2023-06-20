package com.example.messmanagmentsystem.dataclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.R

class MyAdapter(private val menuList : ArrayList<menu>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.menu_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = menuList[position]


        holder.day.text= currentitem.Day
        holder.bfast.text=currentitem.Bfast
        holder.lunch.text= currentitem.lunch
        holder.dinner.text= currentitem.Dinner


    }

    override fun getItemCount(): Int {

        return menuList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val day : TextView = itemView.findViewById(R.id.tvday1)
        val bfast : TextView = itemView.findViewById(R.id.tvbfast1)
        val lunch : TextView = itemView.findViewById(R.id.tvlunch1)
        val dinner : TextView = itemView.findViewById(R.id.tvdinner1)

    }

}
