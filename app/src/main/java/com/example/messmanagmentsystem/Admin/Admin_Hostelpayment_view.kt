package com.example.messmanagmentsystem.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.Admin.MyAdapter_Hostelpayment
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.hostelPayment
import com.google.firebase.database.*

class Admin_Hostelpayment_view : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<hostelPayment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_hostelpayment_view)

        userRecyclerview = findViewById(R.id.hostelpaymentlist)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<hostelPayment>()
        getUserData()

    }

    private fun getUserData() {
        val payhist: TextView = findViewById(R.id.payhist)
        dbref = FirebaseDatabase.getInstance().getReference("tbl_HostelPayment")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(hostelPayment::class.java)
                        userArrayList.add(user!!)

                    }
                    userRecyclerview.adapter = MyAdapter_Hostelpayment(userArrayList)
                } else {
                    payhist.setText("no history available! ")


                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    override fun onBackPressed() {
        var intent= Intent(this, AdminHome1::class.java)
        startActivity(intent)
    }

}