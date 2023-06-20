package com.example.messmanagmentsystem.Users

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.messmanagmentsystem.MainActivity
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.home
import com.google.firebase.database.*

class User_MenuView : AppCompatActivity() {
    val Day:Array<String> = arrayOf("Select Day","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
    private lateinit var Bfast1: TextView
    private lateinit var lunch1: TextView
    private lateinit var Dinner1: TextView
    private lateinit var dbref: DatabaseReference
    var Email: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu_view)
        var sp: Spinner =findViewById(R.id.inspinner1)
        var day:String=""
        Email= intent.getStringExtra("Email").toString()
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.select_dialog_item,Day)
        sp.setAdapter(adapter)
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                day= Day[p2]

                if(day=="Select Day")
                {
                    showmsg()
                }
                else
                {
                    showmenu(day)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }


    }
    fun showmsg()
    {
        Toast.makeText(this,"First select the day from Dropdown",Toast.LENGTH_LONG).show()
    }

    private fun showmenu(day: String) {
        Bfast1= findViewById(R.id.Breakfast1)
        lunch1= findViewById(R.id.Lunch1)
        Dinner1 =findViewById(R.id.Dinner1)


        dbref = FirebaseDatabase.getInstance().getReference("tbl_Menu")

        val query = dbref.orderByChild("day").equalTo(day)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot in snapshot.children)
                {
                    Bfast1.text= userSnapshot.child("bfast").value.toString()
                    lunch1.text= userSnapshot.child("lunch").value.toString()
                    Dinner1.text=userSnapshot.child("dinner").value.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("My", "Failed to read value.")
            }
        } )

    }
    override fun onBackPressed() {

        var intent= Intent(this, home::class.java)
        intent.putExtra("Email",Email.toString())
        startActivity(intent)

    }

}