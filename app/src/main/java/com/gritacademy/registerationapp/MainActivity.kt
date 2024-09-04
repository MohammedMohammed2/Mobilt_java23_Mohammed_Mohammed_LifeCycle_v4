package com.gritacademy.registerationapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var RegisterBtn:Button = findViewById(R.id.button2);
        var LogInBtn:Button = findViewById(R.id.button);

        RegisterBtn.setOnClickListener(View.OnClickListener {
            val registerIntent = Intent(this,RegisterPageActivity::class.java);
            startActivity(registerIntent);
        });
        LogInBtn.setOnClickListener(View.OnClickListener {
            val loginIntent = Intent(this,LogInIntent::class.java);
            startActivity(loginIntent)
        })
    }
}