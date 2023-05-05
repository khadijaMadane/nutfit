package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        auth = Firebase.auth
        setContentView(R.layout.activity_sign_in)
        var btSignin = findViewById<Button>(R.id.loginButton)
        var btSignInSignUp = findViewById<TextView>(R.id.bt_SignIn_phone_SignUp)
        var btnResetPassword = findViewById<TextView>(R.id.logintextForget)
        btSignInSignUp.setOnClickListener{
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        btnResetPassword.setOnClickListener{
            val intent = Intent(this,reset_pass::class.java)
            startActivity(intent)
        }
        btSignin.setOnClickListener {
            performSignIn()
        }
}

    private fun performSignIn() {
        val email = findViewById<EditText>(R.id.login_email)
        val password = findViewById<EditText>(R.id.login_password)

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this,"please fill the fields", Toast.LENGTH_SHORT).show()
            return
        }
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()
        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithEmail:success")
                    val verification = auth.currentUser?.isEmailVerified
                    if(verification==true){
                        val user = auth.currentUser
                        updateUI(user)
                    }
                    else{
                        Toast.makeText(this,"please verify your  email", Toast.LENGTH_SHORT).show()

                    }

                } else {
                    // If sign in fails, display a message to the user.
                   // Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, IngregientsName::class.java)
        startActivity(intent)
    }


}