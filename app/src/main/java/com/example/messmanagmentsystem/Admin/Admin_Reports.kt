package com.example.messmanagmentsystem.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.Myadapter_pending
import com.example.messmanagmentsystem.dataclass.visitor
import com.google.firebase.database.*

class Admin_Reports : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var dbref1: DatabaseReference
    private var thostelstud:Int=0
    private var tstud:Int=0
    private var tteach:Int=0
    private var hpay:Int=0
    private var penpay:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_reports)

        var hostel:TextView=findViewById(R.id.thostel)
        var stud:TextView=findViewById(R.id.tstud)
        var teach:TextView=findViewById(R.id.tteach)

        var hpay1:TextView=findViewById(R.id.thpayment)
        var pen:TextView=findViewById(R.id.thpenpayment)
        dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val pid=userSnapshot.child("pid").value

                        if(pid.toString() == "1")
                        {
                            thostelstud= thostelstud+1
                        }
                        else if(pid.toString() == "2")
                        {
                            tstud= tstud+1
                        }
                        else if(pid.toString() == "3")
                        {
                            tteach= tteach+1
                        }
                    }
                    hostel.setText(thostelstud.toString())
                    stud.setText(tstud.toString())
                    teach.setText(tteach.toString())

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })





        dbref = FirebaseDatabase.getInstance().getReference("tbl_HostelPayment")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {

                        hpay= hpay+1

                    }
                    hpay1.setText(hpay.toString())


                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



        val id= 1.toDouble()
        dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")
        val query = dbref.orderByChild("pid").equalTo(id)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot in snapshot.children)
                {

                    val number1= userSnapshot.child("phone_no").value

                    dbref1 = FirebaseDatabase.getInstance().getReference("tbl_HostelPayment")
                    dbref1.addValueEventListener(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(userSnapshot1 in snapshot.children)
                            {
                                val number2= userSnapshot1.child("phone_no").value
                                if (number1!=number2)
                                {
                                    penpay= penpay+1
                                }
                            }
                            pen.setText(penpay.toString())
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })


                }
            }
            override fun onCancelled(error: DatabaseError) {
                //Log.d("My", "Failed to read value.")

            }
        } )






    }


    override fun onBackPressed() {

        var intent= Intent(this, AdminHome1::class.java)
        startActivity(intent)

    }
}