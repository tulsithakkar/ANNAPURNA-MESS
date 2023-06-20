package com.example.messmanagmentsystem

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var admin:RadioButton=findViewById(R.id.admin)
        var hstud:RadioButton=findViewById(R.id.hostels)
        var stud:RadioButton=findViewById(R.id.students)

        var teach:RadioButton=findViewById(R.id.teachers)
        var ok:Button= findViewById(R.id.ok)

        ok.setOnClickListener {
            var name:String
            if(admin.isChecked || hstud.isChecked || stud.isChecked || teach.isChecked )
            {
                if (admin.isChecked)
                {
                    name="Admin"
                }
                else if (hstud.isChecked)
                {
                    name= "hostel student"
                }
                else if (stud.isChecked)
                {
                    name = "student"
                }
                else
                {
                    name= "teacher"
                }
                val intent = Intent(this,Login::class.java)
                intent.putExtra("name",name.toString())
                startActivity(intent)
            }
            else {
                Toast.makeText(this,"Pless select one option",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {

            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message!!")
            dialogBuilder.setMessage("Are you sure you want to Exit ??")
            dialogBuilder.setPositiveButton("Yes" , {dialogInterface:DialogInterface,i:Int ->finishAffinity()})
            dialogBuilder.setNegativeButton("No",{dialogInterface:DialogInterface,i:Int->})

            dialogBuilder.show();

    }
}