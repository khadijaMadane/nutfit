package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_in)
        var btSignInSignUp = findViewById<TextView>(R.id.bt_SignIn_phone_SignUp)
        btSignInSignUp.setOnClickListener{
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
    }
}
}