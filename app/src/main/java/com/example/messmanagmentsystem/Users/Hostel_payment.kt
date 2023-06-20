package com.example.messmanagmentsystem.Users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.hostelPayment
import com.example.messmanagmentsystem.dataclass.userpayment
import com.example.messmanagmentsystem.home
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class hostel_payment : AppCompatActivity() {
    private lateinit var dbref1: DatabaseReference
    private lateinit var number: TextView
    private lateinit var name: TextView
    var Email: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hostel_payment)

        Email= intent.getStringExtra("Email").toString()
        var pay: Button =findViewById(R.id.btnpay)
        var amount:TextView=findViewById(R.id.tvamount)
        amount.setText("19200")

        number=findViewById(R.id.number)
        name=findViewById(R.id.name1)
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

        var rcash: RadioButton = findViewById(R.id.rbtcash)
        var rupi: RadioButton =findViewById(R.id.rbtnUPI)
        val refid: EditText =findViewById(R.id.tvrefid)
        var type1:String=""
        rcash.setOnClickListener {
            type1="Cash"
            Toast.makeText(this, "please Enter bill/ receipt number as reference id!", Toast.LENGTH_LONG).show()
        }
        rupi.setOnClickListener {
            type1="UPI"
            Toast.makeText(this, "please Enter UPI id as reference id!", Toast.LENGTH_LONG).show()
        }

        pay.setOnClickListener {
           val amount1= amount.text.toString()
            val refid1= refid.text.toString()
            if(amount1.isNotEmpty() && refid1.isNotEmpty() && type1.isNotEmpty())
            {
                val currentDate = Date(System.currentTimeMillis())
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val Date = dateFormat.format(currentDate)
                saveHostelPaymentdata(amount1,type1,refid1,Date)
            }
            else
            {
                Toast.makeText(this,"Fill all the details!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveHostelPaymentdata( amount1: String,type1: String, refid1: String, date: String) {

        dbref1 = FirebaseDatabase.getInstance().getReference("tbl_HostelPayment")
        val amount1= amount1.toString()
        val type= type1.toString()
        val refid= refid1.toString()
        val date1=date.toString()
        val paymentid= dbref1.push().key!!

            val userpay = hostelPayment(number.text.toString(),name.text.toString(),amount1,type,refid,date1)
            dbref1 = FirebaseDatabase.getInstance().getReference("tbl_HostelPayment")
            dbref1.child(paymentid).setValue(userpay)
                .addOnCompleteListener {
                    Toast.makeText(this,"Payment done", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this,"not done", Toast.LENGTH_LONG).show()
                }

    }
    override fun onBackPressed() {
        var intent= Intent(this, home::class.java)
        intent.putExtra("Email",Email.toString())
        startActivity(intent)

    }
}