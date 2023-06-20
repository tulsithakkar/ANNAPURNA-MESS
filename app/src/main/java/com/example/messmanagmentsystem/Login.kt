package com.example.messmanagmentsystem
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.messmanagmentsystem.Admin.AdminHome1
//import com.example.messmanagmentsystem.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    private lateinit var firebaseauth: FirebaseAuth

    private lateinit var dbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseauth = FirebaseAuth.getInstance()


        var newacc: TextView = findViewById(R.id.cnew)
        var name = intent.getStringExtra("name")
        var id: EditText = findViewById(R.id.id)

        var Email: String
        var pass: EditText = findViewById(R.id.pass)
        var logbtn: Button = findViewById(R.id.log)
        var canbtn: Button = findViewById(R.id.can)
        var save: Int = 1

        if (name == "Admin") {
            newacc.visibility = View.GONE
        }

        fun loadData()          //loading the data in login from
        {
            val sharedPreference = getSharedPreferences("FirstPrefereence", MODE_PRIVATE)
            val s1 = sharedPreference.getString("id", "")
            val a = sharedPreference.getString("pass", "")
            id.setText(s1)
            pass.setText(a)
        }

        try {
            loadData()
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }

        fun save()       //shared preference
        {
            val sharedPreference = getSharedPreferences("FirstPrefereence", MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("id", id.text.toString())
            editor.putString("pass", pass.text.toString())
            editor.apply()
            save = 1
            Toast.makeText(this, "saved", Toast.LENGTH_LONG).show()
        }
        newacc.setOnClickListener() //move to regisration page
        {
            val intent = Intent(this, register::class.java)
            intent.putExtra("name", name.toString())
            startActivity(intent)
        }
        canbtn.setOnClickListener {

            id.text = null //cancal button click
            pass.text = null
            save = 0
        }
        fun move(email: String, pass :String)      //move to next page
        {
            if (name == "Admin"){
                if( email== "Admin@gmail.com" && pass=="Admin@12" ) {
                    val intent = Intent(this, AdminHome1::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this,"please check again!!", Toast.LENGTH_LONG).show()
                }
            } else
            {
                if( email!= "Admin@gmail.com" && pass!="Admin@12" )
                {
                    val intent = Intent(this, home::class.java)
                    var Email1= id.text.toString()
                    intent.putExtra("Email", Email1.toString())
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this,"please check again!!", Toast.LENGTH_LONG).show()
                }

            }
        }
        logbtn.setOnClickListener {
            val email = id.text.toString()
            val pass = pass.text.toString()
            if (email.isEmpty() && pass.isEmpty()) {
                Toast.makeText(this, "Please Enter Credential", Toast.LENGTH_LONG).show()
            } else {
                firebaseauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (save == 0) {
                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("Message!!")
                            dialogBuilder.setMessage("Do you want to save id and password??")
                            dialogBuilder.setPositiveButton(
                                "Yes",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.cancel();
                                    save()
                                    move(email,pass)
                                })
                            dialogBuilder.setNegativeButton(
                                "No",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.cancel();
                                    move(email,pass)
                                })
                            dialogBuilder.show();
                        } else {
                            move(email,pass)
                        }
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }
}
