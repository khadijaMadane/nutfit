package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.nutfit.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var etEmail: TextView
    private lateinit var etPassword: TextView
    private lateinit var etConfirm: TextView
    private lateinit var btVerify: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()




        auth = Firebase.auth
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var btSignUpSignIn = findViewById<TextView>(R.id.bt_Signup_phone_Signin)
        btSignUpSignIn.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        // binding = ActivitySignUpBinding.inflate(LayoutInflater)
        setContentView(binding.root)
        etEmail = findViewById(R.id.SignUp_email)
        // etPhone = findViewById(R.id.et_signup_phone)
        etPassword = findViewById(R.id.SignUp_password)
        etConfirm = findViewById(R.id.SignUp_Conf_password)
        btVerify = findViewById(R.id.SignUp_Button)


        btVerify.setOnClickListener {
            performSignUpEmail()


        }
    }
        private fun performSignUpEmail() {
            if (etEmail.text.isEmpty() || etPassword.text.isEmpty()){
                Toast.makeText(this,"please fill the fields",Toast.LENGTH_SHORT).show()
                return
            }
            if(binding.SignUpPassword.text.toString() != binding.SignUpConfPassword.text.toString()){
                binding.SignUpPassword.error="password do not match"
                //  binding.SignUpPassword.errorIconDrawable =null

                return
            }
            if(binding.SignUpPassword.length()<=6){
                binding.SignUpPassword.error="password is too short"
                //  binding.SignUpPassword.errorIconDrawable =null

                return
            }

            val inputEmail = etEmail.text.toString().trim()
            val inputPassword = etPassword.text.toString().trim()
            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.sendEmailVerification()
                            ?.addOnSuccessListener {
                                Toast.makeText(this,"please verify your  email", Toast.LENGTH_SHORT).show()

                            }
                        // Sign in success, update UI with the signed-in user's information
                        //  Log.d(TAG, "createUserWithEmail:success")
                       val user = auth.currentUser
                       updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        //   Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        // updateUI(null)
                    }
                }


        }





    private fun updateUI(user: FirebaseUser?) {
            val intent = Intent(this, DashboardMainActivity::class.java)
            startActivity(intent)
        }

}
/*
        binding.SignUpButton.setOnClickListener {
            val email =binding.SignUpEmail.text.toString()
            val password =binding.SignUpPassword.text.toString()
            //email= findViewById(R.id.SignUpEmail)
           // password= findViewById(R.id.SignUpPassword)
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
        }*/

    /*private fun checkAllField(): Boolean{
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
          //    binding.SignUpPassword.errorIconDrawable =null

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
    }*/
