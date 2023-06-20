package com.example.messmanagmentsystem.dataclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.R

class MyAdapter1(private val reviewlist : ArrayList<rating>) : RecyclerView.Adapter<MyAdapter1.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rating_item,
            parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = reviewlist[position]

        holder.name.text=currentitem.name
        holder.feedback.text=currentitem.Suggestion
        holder.point.text=currentitem.Rating_point
        holder.date.text= currentitem.Date
    }
    override fun getItemCount(): Int {
        return reviewlist.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.tvname1)
        val feedback : TextView = itemView.findViewById(R.id.tvfeed1)
        val point : TextView = itemView.findViewById(R.id.tvpoint1)
        val date:TextView= itemView.findViewById(R.id.tvdate1)
    }
}