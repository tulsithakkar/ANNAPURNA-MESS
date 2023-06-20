package com.example.messmanagmentsystem.Users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.example.messmanagmentsystem.R
import com.example.messmanagmentsystem.dataclass.rating
import com.example.messmanagmentsystem.home
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class user_rating_feedback : AppCompatActivity() {

    private lateinit var suggetion: EditText
    private lateinit var rt: RatingBar
    private lateinit var btnRating: Button
    private lateinit var dbref1: DatabaseReference
    private lateinit var dbref: DatabaseReference
    lateinit var phone_number:String
    var Email: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_rating_feedback)
        Email= intent.getStringExtra("Email").toString()
      suggetion=findViewById(R.id.sugg)
        rt=findViewById(R.id.rating)
        btnRating=findViewById(R.id.btnok)
        dbref1 = FirebaseDatabase.getInstance().getReference("tbl_rating")
        var rating: String? =null
        //rating point from the star
        rt.setOnRatingBarChangeListener { ratingBar, fl, b ->

            rating=fl.toString()
        }
        var mail= intent.getStringExtra("Email")
        var Email= mail.toString()


        val number1:TextView=findViewById(R.id.number)
        val name1:TextView=findViewById(R.id.name1)
        dbref = FirebaseDatabase.getInstance().getReference("tbl_visitor")
        val query = dbref.orderByChild("email").equalTo(Email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userSnapshot in snapshot.children)
                {
                    val number= userSnapshot.child("phone_no").value
                    val name= userSnapshot.child("name").value

                    Log.d("My", "to read value." )

                    number1.setText(number.toString())
                    name1.setText(name.toString())


                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("My", "Failed to read value.")
            }
        } )



        btnRating.setOnClickListener {

            val currentDate = Date(System.currentTimeMillis())
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val Date = dateFormat.format(currentDate)
            val number=number1.text.toString()
            val name= name1.text.toString()
            //Toast.makeText(this,number,Toast.LENGTH_LONG).show()

            saveRating(rating,number,name,Date)
        }
    }

    private fun saveRating(rating: String?, number: String,name :String, date: String) {
        var cphone= number.toString()
        var suggestion= suggetion.text.toString()
        var point=rating.toString()
        var date= date.toString()
        var name= name.toString()

        if (suggestion.isNotEmpty()&& point.isNotEmpty())
        {
            val Rating = rating(cphone,name,suggestion,point,date)
            dbref1 = FirebaseDatabase.getInstance().getReference("tbl_rating")
            dbref1.child(cphone.toString()).setValue(Rating)
                .addOnCompleteListener {
                    Toast.makeText(this,"Done",Toast.LENGTH_LONG).show()


                }.addOnFailureListener {
                    Toast.makeText(this,"not done",Toast.LENGTH_LONG).show()
                }
        }
        else
        {
            Toast.makeText(this,"Please give suggestion and rating points",Toast.LENGTH_LONG).show()
        }


    }
    override fun onBackPressed() {
        var intent= Intent(this, home::class.java)
        intent.putExtra("Email",Email.toString())
        startActivity(intent)
    }


}
