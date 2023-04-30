package com.example.nutfit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class resetEmail : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val hide = supportActionBar?.hide()

        setContentView(R.layout.activity_reset_email)
        val btnRsetDesign = findViewById<Button>(R.id.loginButtonEmail)
        btnRsetDesign.setOnClickListener {
            val intent2 = Intent(this,SignIn::class.java)
            startActivity(intent2)
        }

        var btnResend = findViewById<TextView>(R.id.bt_resend_pass)

        btnResend.setOnClickListener{
            val intent = Intent(this,SignIn::class.java)
            startActivity(intent)
        }

    }
}