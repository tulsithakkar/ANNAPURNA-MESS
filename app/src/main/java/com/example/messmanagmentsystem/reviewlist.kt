package com.example.messmanagmentsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagmentsystem.dataclass.MyAdapter1
import com.example.messmanagmentsystem.dataclass.rating
import com.google.firebase.database.*

class reviewlist : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<rating>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviewlist)
        userRecyclerview = findViewById(R.id.reviewList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<rating>()
        getratingData()
    }
    private fun getratingData() {

        dbref = FirebaseDatabase.getInstance().getReference("tbl_rating")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(rating::class.java)
                        userArrayList.add(user!!)

                    }
                    userRecyclerview.adapter = MyAdapter1(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}