package com.example.messmanagmentsystem.dataclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.R

class Myadapter_member(private val hostelmemberlist : ArrayList<visitor>) : RecyclerView.Adapter<Myadapter_member.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.member_item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return hostelmemberlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = hostelmemberlist[position]


        holder.name.text= currentitem.name
        holder.Email.text=currentitem.email
        holder.phone.text= currentitem.phone_no
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.tvname11)
        val Email : TextView = itemView.findViewById(R.id.tvEmail11)
        val phone : TextView = itemView.findViewById(R.id.tvphone1)

    }

}