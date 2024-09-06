package com.gritacademy.registerationapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class LogInIntent : AppCompatActivity() {
    //loacal variables
    lateinit var auth: FirebaseAuth
    lateinit var doneLoginBtn: Button
    lateinit var emailLogInField: TextView
    lateinit var passwordLogInField: TextView
    lateinit var sharedpref:SharedPreferences
    lateinit var editor:Editor
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_intent)

        //helps track the loged in person
        auth = FirebaseAuth.getInstance()

        //variables for textview and buttons
        doneLoginBtn = findViewById(R.id.doneLogIn)
        emailLogInField = findViewById(R.id.emailLogIn)
        passwordLogInField = findViewById(R.id.passwordLogIn)

        //helps saving last inputed value
        sharedpref=getSharedPreferences("storedData", Context.MODE_PRIVATE)
        editor = sharedpref.edit()


        //intent for next page
        val signedIn =Intent(this,SignedInPage::class.java)


        val db = Firebase.firestore

        //when clicking the button after filling in a fields it will log the user in and show a toast to welcome the person with their name
        doneLoginBtn.setOnClickListener(View.OnClickListener {
            var sEmailLogin = emailLogInField.text.toString().trim()
            var sPasswordLogin = passwordLogInField.text.toString().trim()

            if (sEmailLogin != "" && sPasswordLogin != "") {
                auth.signInWithEmailAndPassword(sEmailLogin, sPasswordLogin)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            val logedInUser = auth.currentUser
                            if (logedInUser != null) { db.collection("users").document(logedInUser.uid).get()
                                    .addOnSuccessListener { task ->
                                        val name = task.get("name")
                                        Toast.makeText(
                                            this,
                                            "welcome " + name,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                      startActivity(signedIn)
                                    }
                            }
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"please fill in the credentials",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        val email = emailLogInField.text.toString().trim()
        val password = passwordLogInField.text.toString().trim()

        editor.apply(){
            putString("email",email)
            putString("password",password)
            apply()
        }

    }

    override fun onStart() {
        super.onStart()
        val email = sharedpref.getString("email",null)
        val password = sharedpref.getString("password",null)

        emailLogInField.setText(email)
        passwordLogInField.setText(password)
    }

}
