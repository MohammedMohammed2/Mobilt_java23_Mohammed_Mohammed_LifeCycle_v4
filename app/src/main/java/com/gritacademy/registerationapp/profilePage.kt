package com.gritacademy.registerationapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class profilePage : AppCompatActivity() {
    //local virables
    lateinit var auth: FirebaseAuth
    lateinit var phoneEdit: TextView
    lateinit var nameEdit: TextView
    lateinit var editBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val db = Firebase.firestore

       //textview and buttons
        nameEdit = findViewById(R.id.nameEdit)
        phoneEdit = findViewById(R.id.phoneEdit)
        editBtn = findViewById(R.id.edit)


        //helps track the current logged in user
        auth = FirebaseAuth.getInstance()
        val logedUser = auth.currentUser

        val signOutIntent = Intent(this, MainActivity::class.java)
        val homeIntent = Intent(this,SignedInPage::class.java)
        val profileIntent = Intent(this, profilePage::class.java)

        //menu
        val menu: Spinner = findViewById(R.id.menu1)



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
                    Toast.makeText(this@profilePage,"Signed Out",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if (menu.selectedItem == "Profile"){
                    startActivity(profileIntent)
                }
                if (menu.selectedItem =="Home"){

                    startActivity(homeIntent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        //gets the name aand phone number of the current user from data base
        if (logedUser != null) {
            db.collection("users").document(logedUser.uid).get().addOnSuccessListener { result ->
                val name = result.get("name")
                val phoneNumber = result.get("phone")

                nameEdit.text = name.toString().trim()
                phoneEdit.text = phoneNumber.toString().trim()            }
        }

        editBtn.setOnClickListener(View.OnClickListener {
            if (nameEdit.text != "" && phoneEdit.text !="" ){
                if (logedUser != null) {
                    db.collection("users").document(logedUser.uid).update("name", nameEdit.text.toString().trim(),"phone",phoneEdit.text.toString().trim())
                    Toast.makeText(this,"user updated",Toast.LENGTH_SHORT).show()
                    startActivity(homeIntent)
                }
            }else{
                Toast.makeText(this,"Please fill in the credentials",Toast.LENGTH_SHORT).show()
            }
        })

    }
}