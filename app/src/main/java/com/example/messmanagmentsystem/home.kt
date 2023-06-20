package com.example.messmanagmentsystem

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.example.messmanagmentsystem.Users.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class home : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var name:String
    private lateinit var dbref: DatabaseReference
    private lateinit var pid1:TextView
    private var pid12:String=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val Email =intent.getStringExtra("Email")
        var tv:TextView=findViewById(R.id.tv)
        tv.setText(Email)
        drawerLayout = findViewById(R.id.drawerLayout)
        var navView: NavigationView = findViewById(R.id.nav_view)

        var Menuv:ImageButton= findViewById(R.id.hmenu)
        var Payment1:ImageButton=findViewById(R.id.hpayment)

        Menuv.setOnClickListener {
            val intent = Intent(this, User_MenuView::class.java)
            intent.putExtra("Email", Email.toString())
            startActivity(intent)
        }

            pid1=findViewById(R.id.hpid)   //coding for button click-------------------------------------->>>>
            dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")
            val query = dbref.orderByChild("email").equalTo(Email)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(userSnapshot in snapshot.children)
                    {
                        val pid=userSnapshot.child("pid").value
                        Log.d("My", "to read value." )
                        pid1.setText(pid.toString())

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("My", "Failed to read value.")
                }
            } )


        Payment1.setOnClickListener {
                //getdata()                 //coding for button click-------------------------------------->>>>
            pid1=findViewById(R.id.hpid)   //coding for button click-------------------------------------->>>>
            dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")
            val query = dbref.orderByChild("email").equalTo(Email)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(userSnapshot in snapshot.children)
                    {
                        val pid=userSnapshot.child("pid").value
                        Log.d("My", "to read value." )
                        pid1.setText(pid.toString())
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("My", "Failed to read value.")
                }
            } )
             pid12= pid1.text.toString()
            if(pid12== "1" )
            {
                val intent = Intent(this, hostel_payment::class.java)
                intent.putExtra("Email", Email.toString())
                startActivity(intent)
            }
            else if(pid12== "2" || pid12== "3")
            {
                val intent = Intent(this, student_teacher_payment::class.java)
                intent.putExtra("Email", Email.toString())
                startActivity(intent)
            }

        }
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            it.isChecked=true
            if(it.itemId == R.id.nav_paymentH)
            {
                pid1=findViewById(R.id.hpid)       //coding for button click-------------------------------------->>>>
                dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")
                val query = dbref.orderByChild("email").equalTo(intent.getStringExtra("Email"))
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for(userSnapshot in snapshot.children)
                        {
                            val pid=userSnapshot.child("pid").value
                            Log.d("My", "to read value." )
                            pid1.setText(pid.toString())
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("My", "Failed to read value.")
                    }
                } )
                if(pid1.text.toString()== "1" )
                {
                    val intent = Intent(this, hostel_payment_hist::class.java)
                    intent.putExtra("Email", Email.toString())
                    startActivity(intent)
                }
                else if(pid1.text.toString()== "2" || pid1.text.toString()== "3")
                {
                    val intent = Intent(this, user_payment_hist::class.java)
                    intent.putExtra("Email", Email.toString())
                    startActivity(intent)
                }
            }
            else if(it.itemId == R.id.nav_review)
            {
                val intent = Intent(this, reviewlist::class.java)
                intent.putExtra("Email", Email.toString())
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_rf)
            {
                val intent = Intent(this, user_rating_feedback::class.java)
                intent.putExtra("Email", Email.toString())
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_contact)
            {
                val intent = Intent(this, Contactus::class.java)
                intent.putExtra("Email", Email.toString())
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_logout)
            {
                val dialogBuilder= AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message!!")
                dialogBuilder.setMessage("Are you sure you want to logout ??")
                dialogBuilder.setPositiveButton("Yes" , DialogInterface.OnClickListener{
                        dialog, id->dialog.cancel();
                    var intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                })
                dialogBuilder.setNegativeButton("No", DialogInterface.OnClickListener{
                        dialog,id->dialog.cancel();
                })
                dialogBuilder.show();
            }
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message!!")
            dialogBuilder.setMessage("Are you sure you want to Exit ??")
            dialogBuilder.setPositiveButton("Yes" , DialogInterface.OnClickListener{
                dialog,id->dialog.cancel();
                var intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            })
            dialogBuilder.setNegativeButton("No", DialogInterface.OnClickListener{
                    dialog,id->dialog.cancel();
            })
            dialogBuilder.show();
    }
}