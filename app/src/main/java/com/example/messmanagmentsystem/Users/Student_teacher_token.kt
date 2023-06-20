package com.example.messmanagmentsystem.Users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.home

class Student_teacher_token : AppCompatActivity() {
    var Email1=" "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_teacher_token)

        var Email:TextView= findViewById(R.id.tvmail)
        var date:TextView= findViewById(R.id.tvdate)
        var type:TextView=findViewById(R.id.tvtype)
        var amount:TextView=findViewById(R.id.tvamount)
        Email1= intent.getStringExtra("Email").toString()

        Email.setText("Email:  " + intent.getStringExtra("Email"))
        date.setText("Date:  " + intent.getStringExtra("date"))
        type.setText("Type: " + intent.getStringExtra("type"))
        amount.setText("Amount: " +intent.getStringExtra("amount")+ "rs")


        Toast.makeText(this,"Please Take a ScreenShort of Token",Toast.LENGTH_LONG).show()

    }
    override fun onBackPressed() {

            var intent= Intent(this, home::class.java)
            intent.putExtra("Email", Email1.toString())
            startActivity(intent)

    }
}