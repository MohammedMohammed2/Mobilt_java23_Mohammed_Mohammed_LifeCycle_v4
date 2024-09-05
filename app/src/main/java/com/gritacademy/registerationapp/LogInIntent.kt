package com.gritacademy.registerationapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class LogInIntent : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_intent)
        auth = FirebaseAuth.getInstance()
        var doneLoginBtn: Button = findViewById(R.id.doneLogIn)
        var emailLogInField: TextView = findViewById(R.id.emailLogIn)
        var passwordLogInField: TextView = findViewById(R.id.passwordLogIn)
        val db = Firebase.firestore




        doneLoginBtn.setOnClickListener(View.OnClickListener {
            var sEmailLogin = emailLogInField.text.toString().trim()
            var sPasswordLogin = passwordLogInField.text.toString().trim()


            auth.signInWithEmailAndPassword(sEmailLogin, sPasswordLogin)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val logedInUser = auth.currentUser
                        if (logedInUser != null) {
                            db.collection("users").document(logedInUser.uid).get().addOnSuccessListener {task->
                                val name = task.get("name")
                                Toast.makeText(
                                    this,
                                    "welcome " + name ,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

        })
    }
}
