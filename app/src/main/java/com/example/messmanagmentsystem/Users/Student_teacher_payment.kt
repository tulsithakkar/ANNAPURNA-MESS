package com.example.messmanagmentsystem.Users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.userpayment
import com.example.messmanagmentsystem.home
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class student_teacher_payment : AppCompatActivity() {

    val Day:Array<String> = arrayOf("Select Day","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
    val type:Array<String> = arrayOf("Select","Breakfast","Lunch","Dinner")

    private lateinit var dbref1: DatabaseReference
    private lateinit var number:TextView
    private lateinit var name:TextView
    var Email: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_teacher_payment)

        Email= intent.getStringExtra("Email").toString()

        var sp: Spinner =findViewById(R.id.tvspinner)
        var day:String=""
        var pay:Button=findViewById(R.id.btnpay)
        var amount:TextView=findViewById(R.id.tvamount)

        number=findViewById(R.id.number)
        name=findViewById(R.id.name1)


        var sp1: Spinner =findViewById(R.id.tvspinner1)
        var day1:String=""

        val adapter1: ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.select_dialog_item,Day)
        sp1.setAdapter(adapter1)
        sp1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Toast.makeText(this@MainActivity,Occupation[p2],Toast.LENGTH_LONG).show()
                day1 = Day[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        if(day1=="Select Day")
        {
            Toast.makeText(this,"Select day first!",Toast.LENGTH_LONG).show()
        }


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.select_dialog_item,type)
        sp.setAdapter(adapter)
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Toast.makeText(this@MainActivity,Occupation[p2],Toast.LENGTH_LONG).show()
                day= type[p2]
                if(day=="Breakfast")
                {
                    amount.setText("30")
                }
                else if(day=="Lunch")
                {
                    if(day1=="Friday")
                    {
                        amount.setText("90")
                    }
                    else
                    {
                        amount.setText("70")
                    }
                }
                else if(day=="Dinner")
                {
                    amount.setText("70")
                }
                else
                {
                    amount.setText("Select time")
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        var Email= intent.getStringExtra("Email")

        dbref1 = FirebaseDatabase.getInstance().getReference("tbl_visitor")
        val query = dbref1.orderByChild("email").equalTo(Email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot in snapshot.children)
                {
                    val number1= userSnapshot.child("phone_no").value
                    val name1= userSnapshot.child("name").value
                    Log.d("My", "to read value." )
                    number.setText(number1.toString())
                    name.setText(name1.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("My", "Failed to read value.")
            }
        } )

        var rcash:RadioButton= findViewById(R.id.rbtcash)
        var rupi:RadioButton=findViewById(R.id.rbtnUPI)
        var type1:String=""
        rcash.setOnClickListener {
            type1="Cash"
            Toast.makeText(this, "please Enter bill/ receipt number as reference id!", Toast.LENGTH_LONG).show()
        }
        rupi.setOnClickListener {
            type1="UPI"
            Toast.makeText(this, "please Enter UPI id as reference id!", Toast.LENGTH_LONG).show()
        }

        val refid:EditText=findViewById(R.id.tvrefid)
        pay.setOnClickListener {
            val refid1= refid.text.toString()
            if (day1 == "Select Day") {
                Toast.makeText(this, "select the day!", Toast.LENGTH_LONG).show()
            }
            else if (day == "Select") {
                    Toast.makeText(this, "select the time!", Toast.LENGTH_LONG).show()
                }
            else
            {
                if(type1=="")
                {
                    Toast.makeText(this, "select payment type!", Toast.LENGTH_LONG).show()
                }
                else
                {
                    //Toast.makeText(this, type1.toString(), Toast.LENGTH_LONG).show()
                    if (refid1.isEmpty())
                    {

                        Toast.makeText(this, "Enter Referenceid", Toast.LENGTH_LONG).show()
                    }
                    else{
                        var amount1= amount.text.toString()
                       saveuserpaymentdata(day1,day,type1,refid1,amount1)

                    }
                }
            }
        }
    }

    private fun saveuserpaymentdata(day1: String, day: String, type1: String, refid1: String,amount: String) {
        val currentDate = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val Date = dateFormat.format(currentDate)
        dbref1 = FirebaseDatabase.getInstance().getReference("tbl_UserPayment")
        val paymentid= dbref1.push().key!!
        val time1=day.toString()
        val day= day1.toString()
        val amount1= amount.toString()
        val type= type1.toString()
        val refid= refid1.toString()
        val date1=Date.toString()
        var Email= intent.getStringExtra("Email")

        val userpay = userpayment(paymentid,number.text.toString(),name.text.toString(),day,time1,amount1,type,refid,date1)
        dbref1.child(paymentid).setValue(userpay)
            .addOnCompleteListener {
                //Toast.makeText(this,"Payment done",Toast.LENGTH_LONG).show()
                val intent = Intent(this, Student_teacher_token::class.java)
                intent.putExtra("date", date1.toString())
                intent.putExtra("type", time1.toString())
                intent.putExtra("amount", amount1.toString())
                intent.putExtra("Email", Email.toString())
                startActivity(intent)


            }.addOnFailureListener {
                Toast.makeText(this,"not done",Toast.LENGTH_LONG).show()
            }
    }
    override fun onBackPressed() {
        var intent= Intent(this, home::class.java)
        intent.putExtra("Email",Email.toString())
        startActivity(intent)
    }
}