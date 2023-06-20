package com.example.messmanagmentsystem.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.MyAdapter_userpayment
import com.example.messmanagmentsystem.dataclass.userpayment
import com.google.firebase.database.*

class Admin_userPayment_view : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<userpayment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_payment_view)
        userRecyclerview = findViewById(R.id.userpaymentlist1)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<userpayment>()
        getUserData()
    }
    private fun getUserData() {
        val payhist: TextView =findViewById(R.id.payhist)
        dbref = FirebaseDatabase.getInstance().getReference("tbl_UserPayment")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(userpayment::class.java)
                        userArrayList.add(user!!)

                    }
                    userRecyclerview.adapter = MyAdapter_userpayment(userArrayList)
                }
                else
                {
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