package com.example.messmanagmentsystem.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.Myadapter_member
import com.example.messmanagmentsystem.dataclass.hostelPayment
import com.example.messmanagmentsystem.dataclass.visitor
import com.google.firebase.database.*

class memberlist : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<visitor>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memberlist)

        var pid= intent.getStringExtra("pid")
        var txtview:TextView=findViewById(R.id.txtview)
        if (pid=="1")
        {
            txtview.setText("Hostel Student")
        }
        else if(pid=="2")
        {
            txtview.setText("Student")
        }
        else if(pid=="3")
        {
            txtview.setText("Teacher")
        }
        //Toast.makeText(this,pid.toString(),Toast.LENGTH_LONG).show()
        userRecyclerview = findViewById(R.id.memberList1)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<visitor>()
        gethostelData(pid.toString())
    }

    private fun gethostelData(pid:String) {
        var txtview:TextView=findViewById(R.id.txtview)
        var msg1:String=""
        var result:TextView=findViewById(R.id.result)
        val id= pid.toDouble()
        dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")
        val query = dbref.orderByChild("pid").equalTo(id)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot in snapshot.children)
                {
                    val user = userSnapshot.getValue(visitor::class.java)
                    userArrayList.add(user!!)
                }
                userRecyclerview.adapter = Myadapter_member(userArrayList)
                result.setText("data available")
            }
            override fun onCancelled(error: DatabaseError) {
                //Log.d("My", "Failed to read value.")

            }
        } )

    }
    override fun onBackPressed() {
        val intent = Intent(this, AdminHome1::class.java)
        startActivity(intent)
    }
}

class MyAdapter_Hostelpayment(private val hostelpaymentlist : ArrayList<hostelPayment>): RecyclerView.Adapter<MyAdapter_Hostelpayment.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.hostelpayment_item,
            parent,false)
        return  MyAdapter_Hostelpayment.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return hostelpaymentlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = hostelpaymentlist[position]

        holder.name.text= currentitem.name
        holder.amount.text=currentitem.amount
        holder.type.text=currentitem.type
        holder.refid.text=currentitem.refid
        holder.date.text=currentitem.Date

    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.hpayname)
        val amount: TextView =itemView.findViewById(R.id.hpayamount)
        val type: TextView =itemView.findViewById(R.id.hpaytype)
        val refid : TextView = itemView.findViewById(R.id.hpayrefid)
        val date : TextView = itemView.findViewById(R.id.hpaydate)

    }
}