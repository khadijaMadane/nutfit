package com.example.nutfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class changeEmail extends AppCompatActivity {
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private TextView textViewAuthentificated;
    private String userOldEmail, userNewEmail,userPwd;
    private Button buttonUpdateEmail;
    private EditText editTextNewEmail, editTextPwd;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Email");
        progressBar= findViewById(R.id.progressBar);
        editTextPwd= findViewById(R.id.editext_update_email_verify_password);
        editTextNewEmail = findViewById(R.id.editext_update_email_new);
        textViewAuthentificated = findViewById(R.id.textView_update_email_authentificated);
        buttonUpdateEmail= findViewById(R.id.button_update_email);
        buttonUpdateEmail.setEnabled(false);
        editTextNewEmail.setEnabled(false);
        authProfile=FirebaseAuth.getInstance();
        firebaseUser= authProfile.getCurrentUser();
        //set old email on text view
        userOldEmail= firebaseUser.getEmail();
        TextView textViewOldEmail= findViewById(R.id.textView_update_email_old);
        textViewOldEmail.setText(userOldEmail);
        if(firebaseUser.equals("")){
            Toast.makeText(changeEmail.this,"something went wrong! user's detail not available", Toast.LENGTH_LONG).show();

        }else {
            reAuthenticate(firebaseUser);
        }


    }

    private void reAuthenticate(FirebaseUser firebaseUser) {
        Button buttonVerifyUser= findViewById(R.id.button_authenticate_user);
        buttonVerifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain password for authentication
                userPwd = editTextPwd.getText().toString();
                if(TextUtils.isEmpty(userPwd)){
                    Toast.makeText(changeEmail.this,"password is needed to continue", Toast.LENGTH_LONG).show();
                    editTextPwd.setError("please enter your password for authentication");
                    editTextPwd.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    AuthCredential credential= EmailAuthProvider.getCredential(userOldEmail, userPwd);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(changeEmail.this,"password has been verified"+"you can update email now", Toast.LENGTH_LONG).show();
                                textViewAuthentificated.setText("you can authenticated, you can update your email now;");
                                //
                                editTextNewEmail.setEnabled(true);
                                editTextPwd.setEnabled(false);
                                buttonVerifyUser.setEnabled(false);
                                buttonUpdateEmail.setEnabled(true);
                                //change color of update button
                                buttonUpdateEmail.setBackgroundTintList(ContextCompat.getColorStateList(changeEmail.this,R.color.green));
                                buttonUpdateEmail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userNewEmail=editTextNewEmail.getText().toString();
                                        if(TextUtils.isEmpty(userNewEmail)){
                                            Toast.makeText(changeEmail.this,"new email is required", Toast.LENGTH_LONG).show();
                                            editTextNewEmail.setError("please enter new email");
                                            editTextNewEmail.requestFocus();
                                        } else if (userOldEmail.matches(userNewEmail)) {
                                            Toast.makeText(changeEmail.this,"new email cannot be same as old Email", Toast.LENGTH_LONG).show();
                                            editTextNewEmail.setError("please enter new email");
                                            editTextNewEmail.requestFocus();

                                        }else{
                                            progressBar.setVisibility(View.VISIBLE);
                                            updateEmail(firebaseUser);
                                        }
                                    }
                                });
                            }else {
                                try {
                                    throw task.getException();
                                }
                                catch (Exception e){
                                    Toast.makeText(changeEmail.this, e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }

                        }
                    });
                }
            }
        });
    }
    private void updateEmail(  FirebaseUser firebaseUser){
        firebaseUser.updateEmail(userNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    firebaseUser.sendEmailVerification();
                    Toast.makeText(changeEmail.this,"new email has been updated , please verify your new email", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(changeEmail.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}