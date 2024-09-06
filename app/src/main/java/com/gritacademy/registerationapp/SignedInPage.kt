package com.gritacademy.registerationapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignedInPage : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val signOutIntent = Intent(this, MainActivity::class.java)
        val profileIntent = Intent(this, profilePage::class.java)
        //menu
        val menu:Spinner = findViewById(R.id.menu)

        auth = FirebaseAuth.getInstance()

        //menu
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.menu_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        menu.setAdapter(adapter)


        menu.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (menu.selectedItem=="LogOut"){
                    auth.signOut()
                    startActivity(signOutIntent)
                    Toast.makeText(this@SignedInPage,"Signed Out",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if (menu.selectedItem == "Profile"){
                    startActivity(profileIntent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }
}