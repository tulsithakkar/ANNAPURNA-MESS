package com.example.messmanagmentsystem.Admin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.example.messmanagmentsystem.*
import com.google.android.material.navigation.NavigationView

class AdminHome1 : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home1)

        var view:ImageButton= findViewById(R.id.menuview)
        view.setOnClickListener {
            val intent = Intent(this, menulist::class.java)
            startActivity(intent)
        }

        var Mupdate:ImageButton= findViewById(R.id.menuupdate)
        Mupdate.setOnClickListener {
            val intent = Intent(this, Admin_menuupdate::class.java)
            startActivity(intent)
        }



        drawerLayout= findViewById(R.id.drawerLayout_admin)
        var navView: NavigationView = findViewById(R.id.nav_viewadmin)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            it.isChecked=true
            if(it.itemId == R.id.nav_hostelmember)
            {
                var intent= Intent(this, memberlist::class.java)
                intent.putExtra("pid","1")
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_studentmember)
            {
                var intent= Intent(this, memberlist::class.java)
                intent.putExtra("pid","2")
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_teachermember)
            {
                var intent= Intent(this, memberlist::class.java)
                intent.putExtra("pid","3")
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_hostelpayment)
            {
                val intent = Intent(this, Admin_Hostelpayment_view::class.java)
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_STpayment)
            {
                val intent = Intent(this, Admin_userPayment_view::class.java)
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_penpayment)
            {
                var intent= Intent(this, Admin_pendingHpaymentView::class.java)
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_rf1)
            {
                var intent= Intent(this, reviewlist::class.java)
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_repo)
            {
                var intent= Intent(this, Admin_Reports::class.java)
                startActivity(intent)
            }
            else if(it.itemId == R.id.nav_logout1)
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