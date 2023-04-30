package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class reset_pass : AppCompatActivity() {
    private lateinit var etPassword:EditText
    private lateinit var btnnReset:Button
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val hide = supportActionBar?.hide()


        setContentView(R.layout.activity_reset_pass)


        btnnReset=findViewById(R.id.btnReset)
        btnnReset.setOnClickListener {
            resetPassEmailCheck()
            Verificate()


        }
    }
        private fun resetPassEmailCheck(){
        etPassword= findViewById(R.id.resetEmail)
       //     btnnReset=findViewById(R.id.btnReset)
        auth = FirebaseAuth.getInstance()
       // btnnReset.setOnClickListener {
            val sPassword =etPassword.text.toString()
            auth.sendPasswordResetEmail(sPassword).addOnSuccessListener {
              //  Toast.makeText(this,"please check your email", Toast.LENGTH_SHORT).show()

            }
                .addOnSuccessListener {
                   // Toast.makeText(this, it.toString(),Toast.LENGTH_SHORT).show()
                }
        //}

    }

    private fun Verificate() {
        val intent5 = Intent(this, resetEmail::class.java)
        startActivity(intent5)
    }




}