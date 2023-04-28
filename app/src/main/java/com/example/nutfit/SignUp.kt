package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.nutfit.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()


        setContentView(R.layout.activity_sign_up)

        auth=Firebase.auth
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var btSignUpSignIn = findViewById<TextView>(R.id.bt_Signup_phone_Signin)
        btSignUpSignIn.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        binding.SignUpButton.setOnClickListener {
            val email =binding.SignUpEmail.text.toString()
            val password =binding.SignUpPassword.text.toString()
            if(checkAllField()){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        auth.signOut()
                        Toast.makeText(this,"account created successfully", Toast.LENGTH_SHORT)
                    }
                    else{
                        Log.e("error:", it.exception.toString())
                    }
                }
            }
        }
    }
    private fun checkAllField(): Boolean{
        val email= binding.SignUpEmail.text.toString()
        if(binding.SignUpEmail.text.toString()==""){
            binding.SignUpEmail.error="this is required field"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.SignUpEmail.error="check email format"
            return false
        }
        if(binding.SignUpPassword.text.toString()==""){
            binding.SignUpPassword.error="this is required field"
            //  binding.SignUpPassword.errorIconDrawable =null

            return false
        }
        if(binding.SignUpPassword.length()<=6){
            binding.SignUpPassword.error="password is too short"
            //  binding.SignUpPassword.errorIconDrawable =null

            return false
        }
        if(binding.SignUpConfPassword.text.toString()==""){
            binding.SignUpConfPassword.error="this is required field"
            //  binding.SignUpPassword.errorIconDrawable =null

            return false
        }
        if(binding.SignUpPassword.text.toString() != binding.SignUpConfPassword.text.toString()){
            binding.SignUpPassword.error="password do not match"
            //  binding.SignUpPassword.errorIconDrawable =null

            return false
        }
        return false
    }
}