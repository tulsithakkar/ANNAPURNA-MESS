package com.example.messmanagmentsystem.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.menu
import com.example.messmanagmentsystem.home
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Admin_menuupdate : AppCompatActivity() {

    val Day: Array<String> = arrayOf(
        "Select Day",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    )

    // private lateinit var Day1: EditText
    private lateinit var Bfast1: EditText
    private lateinit var lunch1: EditText
    private lateinit var Dinner1: EditText
    private lateinit var update: Button
    private lateinit var dbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menuupdate)
        var sp: Spinner = findViewById(R.id.inspinner)
        var day: String = ""

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, Day)
        sp.setAdapter(adapter)
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Toast.makeText(this@MainActivity,Occupation[p2],Toast.LENGTH_LONG).show()
                day = Day[p2]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        Bfast1 = findViewById(R.id.Breakfast)
        lunch1 = findViewById(R.id.Lunch)
        Dinner1 = findViewById(R.id.Dinner)
        update = findViewById(R.id.updateBtn)
        dbref = FirebaseDatabase.getInstance().getReference("tbl_Menu")

        update.setOnClickListener {

            if (day == "Select Day") {
                Toast.makeText(this, "First select the day from Dropdown", Toast.LENGTH_LONG).show()
            } else {
                val day1 = day.toString()
                updateData(day1)
            }


        }
    }


    private fun updateData(day: String) {

        dbref = FirebaseDatabase.getInstance().getReference("tbl_Menu")
        val day = day.toString()
        val bfast = Bfast1.text.toString()
        val lunch = lunch1.text.toString()
        val Dinner = Dinner1.text.toString()
        val user = mapOf<String, String>(
            "day" to day,
            "bfast" to bfast,
            "lunch" to lunch,
            "dinner" to Dinner
        )

        if (day.isNotEmpty() && bfast.isNotEmpty() && lunch.isNotEmpty() && Dinner.isNotEmpty()) {
            dbref.child(day).updateChildren(user).addOnSuccessListener {
                Bfast1.text.clear()
                lunch1.text.clear()
                Dinner1.text.clear()

                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {

                Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()

            }

    }
    else
    {
        Toast.makeText(this, "Fill all the details!!", Toast.LENGTH_SHORT).show()
    }
}


    override fun onBackPressed() {
            var intent= Intent(this, AdminHome1::class.java)
            startActivity(intent)
    }
}