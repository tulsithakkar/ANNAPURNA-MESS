package com.example.messmanagmentsystem.Users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.Admin.MyAdapter_Hostelpayment
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.hostelPayment
import com.example.messmanagmentsystem.home
import com.google.firebase.database.*

class hostel_payment_hist : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<hostelPayment>
    private lateinit var number:TextView
    var Email1=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hostel_payment_hist)

        userRecyclerview = findViewById(R.id.hostelpaymentlist1)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<hostelPayment>()
        val payhist: TextView = findViewById(R.id.payhist)
        Email1= intent.getStringExtra("Email").toString()
        var Email= intent.getStringExtra("Email")
        number=findViewById(R.id.payhist)
        dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")
        val query = dbref.orderByChild("email").equalTo(Email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot in snapshot.children)
                {
                    val number1= userSnapshot.child("phone_no").value
                    Log.d("My", "to read value." )
                    dbref = FirebaseDatabase.getInstance().getReference("tbl_HostelPayment")
                    val query1 = dbref.orderByChild("phone_no").equalTo(number1.toString())
                    query1.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(userSnapshot in snapshot.children)
                            {
                                if(snapshot.exists()) {

                                        val user = userSnapshot.getValue(hostelPayment::class.java)
                                        userArrayList.add(user!!)

                                    userRecyclerview.adapter = MyAdapter_Hostelpayment(userArrayList)
                                }
                                else
                                {
                                    payhist.setText("no history available! ")
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            //Log.d("My", "Failed to read value.")
                        }
                    } )
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("My", "Failed to read value.")
            }
        } )

    }

    override fun onBackPressed() {

        var intent= Intent(this, home::class.java)
        intent.putExtra("Email", Email1.toString())
        startActivity(intent)

    }
}