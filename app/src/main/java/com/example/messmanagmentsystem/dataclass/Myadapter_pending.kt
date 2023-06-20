package com.example.messmanagmentsystem.dataclass

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.R

class Myadapter_pending(private val pendingpaymentlist : ArrayList<visitor>) : RecyclerView.Adapter<Myadapter_pending.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.pending_item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pendingpaymentlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem =  pendingpaymentlist[position]
        val item =  pendingpaymentlist[position]
        holder.bind(item)

        holder.name.text= currentitem.name
        holder.Email.text=currentitem.email
        holder.phone.text= currentitem.phone_no


    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvname11)
        val Email: TextView = itemView.findViewById(R.id.tvEmail11)
        val phone: TextView = itemView.findViewById(R.id.tvphone1)
        val btn: ImageButton = itemView.findViewById(R.id.call)
        fun bind(item: visitor) {
            // Set other views in the layout as needed
            btn.setOnClickListener {
                val phoneNumber = item.phone_no
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}