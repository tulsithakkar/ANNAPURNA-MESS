package com.example.messmanagmentsystem.Users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.home

class Contactus : AppCompatActivity() {
    lateinit var name:String
    var Email: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactus)
        name= intent.getStringExtra("name").toString()
        Email= intent.getStringExtra("Email").toString()
    }
    override fun onBackPressed() {


        var intent= Intent(this, home::class.java)
        intent.putExtra("Email",Email.toString())
        startActivity(intent)

    }

}