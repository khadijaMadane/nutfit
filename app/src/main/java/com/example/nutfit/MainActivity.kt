package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val hide = supportActionBar?.hide()

        setContentView(R.layout.activity_welcome)
        val btSignin = findViewById<Button>(R.id.bt_SignIn)
        btSignin.setOnClickListener {
            val intent2 = Intent(this,SignIn::class.java)
            startActivity(intent2)
        }
        val btSignup = findViewById<Button>(R.id.bt_SignUp)



        btSignup.setOnClickListener {
            val intent3 = Intent(this,SignUp::class.java)
            startActivity(intent3)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent =Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}