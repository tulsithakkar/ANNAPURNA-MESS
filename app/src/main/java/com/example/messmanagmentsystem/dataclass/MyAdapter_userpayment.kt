package com.example.messmanagmentsystem.dataclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.R

class MyAdapter_userpayment(private val userpaymentlist : ArrayList<userpayment>):
    RecyclerView.Adapter<MyAdapter_userpayment.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.userpayment_item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userpaymentlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userpaymentlist[position]

        holder.name.text= currentitem.name
        holder.day.text=currentitem.day
        holder.time.text=currentitem.time
        holder.amount.text=currentitem.amount
        holder.type.text=currentitem.type
        holder.refid.text=currentitem.refid
        holder.date.text=currentitem.Date

    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.payname)
        val day:TextView=itemView.findViewById(R.id.payday)
        val time:TextView=itemView.findViewById(R.id.paytime)
        val amount:TextView=itemView.findViewById(R.id.payamount)
        val type:TextView=itemView.findViewById(R.id.paytype)
        val refid : TextView = itemView.findViewById(R.id.payrefid)
        val date : TextView = itemView.findViewById(R.id.paydate)

    }
}