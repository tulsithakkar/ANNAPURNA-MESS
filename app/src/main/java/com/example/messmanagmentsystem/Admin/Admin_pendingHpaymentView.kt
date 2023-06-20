package com.example.messmanagmentsystem.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.dataclass.Myadapter_pending
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.*
import com.google.firebase.database.*

class Admin_pendingHpaymentView : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var dbref1 : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<visitor>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pending_hpayment_view)

        userRecyclerview = findViewById(R.id.pendinglist)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<visitor>()
        gethostelData()

    }
    private fun gethostelData() {

        dbref1 = FirebaseDatabase.getInstance().getReference("tbl_HostelPayment")

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
                           if (snapshot.exists()) {
                               // Node exists
                               for(userSnapshot1 in snapshot.children)
                               {
                                   val number2= userSnapshot1.child("phone_no").value
                                   if (number1!=number2)
                                   {
                                       val user = userSnapshot.getValue(visitor::class.java)
                                       userArrayList.add(user!!)
                                       userRecyclerview.adapter = Myadapter_pending(userArrayList)
                                   }
                               }
                           } else {
                               // Node does not exist
                               val user = userSnapshot.getValue(visitor::class.java)
                               userArrayList.add(user!!)
                               userRecyclerview.adapter = Myadapter_pending(userArrayList)
                           }
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