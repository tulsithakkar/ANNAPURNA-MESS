package com.example.messmanagmentsystem

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.messmanagmentsystem.databinding.ActivityRegisterBinding
import com.example.messmanagmentsystem.dataclass.visitor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class register : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    private lateinit var firebaseauth:FirebaseAuth
    private lateinit var dbref: DatabaseReference
    private lateinit var dbref1: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseauth=FirebaseAuth.getInstance()
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var type= intent.getStringExtra("name")

        binding.reg.setOnClickListener {

            val name= binding.Name.text.toString()
            val phone_no=binding.mobile.text.toString()
            val email= binding.mail.text.toString()
            val pass= binding.pass1.text.toString()
            val cpass= binding.cpass.text.toString()
            if(name.isNotEmpty() && phone_no.isNotEmpty()&& email.isNotEmpty() && pass.isNotEmpty() && cpass.isNotEmpty())
            {//code remain------

                if (phone_no.length<10)
                {
                    Toast.makeText(this,"please enter 10 digits valid number", Toast.LENGTH_LONG).show()
                }
                else
                {

                    dbref1 = Firebase.database.reference.child("tbl_visitor")
                    dbref1.child(phone_no.toString()).get().addOnSuccessListener {
                        if (it.exists()){
                            Toast.makeText(this,"number  is exits",Toast.LENGTH_SHORT).show()
                        }else{
                            if (pass==cpass)
                            {
                                firebaseauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                                    if(it.isSuccessful)
                                    {
                                        savevisitor()

                                    }
                                    else
                                    {
                                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(this,"password not matched!!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }.addOnFailureListener{

                        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else
            {
                Toast.makeText(this,"pleace enter all the details!!", Toast.LENGTH_LONG).show()
            }
        }

        // if use had logged in than go to login page!!
        binding.cold.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            intent.putExtra("name",type.toString())
            startActivity(intent)
        }

    }
    private fun savevisitor()
    {
        val name= binding.Name.text.toString()
        val phone_no=binding.mobile.text.toString()
        val email= binding.mail.text.toString()
        var type1= intent.getStringExtra("name")
        val ptype1= type1.toString()
        var id:String=""
        dbref = FirebaseDatabase.getInstance().getReference("tbl_PersonType")
        dbref1 = Firebase.database.reference.child("tbl_visitor")
        dbref.child(ptype1).get().addOnSuccessListener {
            if (it.exists()){
                //fetch pid from persone type table
                id = it.child("pid").value.toString()

                //registor the visitor

                val visitor= visitor(name, email, phone_no,id.toInt())
                dbref1.child(phone_no).setValue(visitor)
                    .addOnCompleteListener {


                        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(this@register,
                                arrayOf(android.Manifest.permission.SEND_SMS), 1)
                        } else {
                            val phoneNumber = phone_no.toString()
                            val message = "Hello " + name.toString() + "\n" + "You'r successfully registered in Annapurna mess" + "\n" + "Your username is " +
                                    email.toString() + "\n" + "Your passward is " + binding.pass1.text.toString() + "\n" + "Thanks for register!!"
                            try {
                                val smsManager: SmsManager
                                if (Build.VERSION.SDK_INT>=33) {
                                    smsManager = this.getSystemService(SmsManager::class.java)
                                }
                                else{
                                    smsManager = SmsManager.getDefault()
                                }
                                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                                Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()
                                val intent= Intent(this,Login::class.java)
                                intent.putExtra("name",type1)
                                intent.putExtra("phone",phone_no)
                                startActivity(intent)

                            } catch (e: Exception) {
                                Toast.makeText(applicationContext, "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
                                    .show()
                            }
                        }









                    }.addOnFailureListener {
                        Toast.makeText(this,"please try again to register!!",Toast.LENGTH_LONG).show()
                    }
            }else{
                Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }
    private fun sendSms(name : String , email:String , phone_no:String)
    {
        Toast.makeText(this,"send sms ",Toast.LENGTH_LONG).show()
        var pass= binding.pass1.text.toString()
        var name= name
        var email = email
        var phone_no = phone_no
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@register,
                arrayOf(android.Manifest.permission.SEND_SMS), 1)
        } else {
            val phoneNumber = phone_no.toString()
            val message = "Hello" + name.toString() + "\n" + "You'r successfully registered in Annapurna mess" + "\n" + "Your username is " +
                    email.toString() + "\n" + "Your passward is " + pass.toString() + "\n" + "Thanks for register!!"
            try {
                val smsManager: SmsManager
                if (Build.VERSION.SDK_INT>=33) {
                    smsManager = this.getSystemService(SmsManager::class.java)
                }
                else{
                    smsManager = SmsManager.getDefault()
                }
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}