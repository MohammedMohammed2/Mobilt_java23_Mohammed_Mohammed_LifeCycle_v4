package com.gritacademy.registerationapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import com.google.firebase.firestore.firestore

class RegisterPageActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val homeIntent = Intent(this, MainActivity::class.java)

        var regiDone: Button = findViewById(R.id.Done)
        var nameRegiField: EditText = findViewById(R.id.nameRegi)
        var passwordRegiField: EditText = findViewById(R.id.passwordRegi)
        var emailAdressRegiField: EditText = findViewById(R.id.emailAddressRegi)
        var phoneRegiField: EditText = findViewById(R.id.phoneRegi)


        auth = FirebaseAuth.getInstance()

        regiDone.setOnClickListener(View.OnClickListener {

            val sName = nameRegiField.text.toString().trim()
            val sPassword = passwordRegiField.text.toString().trim()
            val sEmail = emailAdressRegiField.text.toString().trim()
            val sPhone = phoneRegiField.text.toString().trim()

            if (sName != "" && sPassword != "" && sEmail != "" && sPhone != "") {
                auth.createUserWithEmailAndPassword(sEmail, sPassword)
                    .addOnCompleteListener() { result ->
                        if (result.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val db = Firebase.firestore
                            val user = hashMapOf(
                                "name" to sName,
                                "password" to sPassword,
                                "email" to sEmail,
                                "phone" to sPhone,
                            )
                            if (userId != null) {
                                db.collection("users").document(userId).set(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Registration successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        startActivity(homeIntent)
                                    }.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Registration Failed",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, result.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                nameRegiField.text.clear()
                passwordRegiField.text.clear()
                emailAdressRegiField.text.clear()
                phoneRegiField.text.clear()

            } else {
                Toast.makeText(this, "Please fill in your credentials", Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }
}