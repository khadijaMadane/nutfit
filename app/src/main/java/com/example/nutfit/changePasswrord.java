package com.example.nutfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class changePasswrord extends AppCompatActivity {
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar1;
    private TextView textViewAuthentificated;
    private String userPwdCurr;
    private Button buttonChangePwd, buttonReAuthentificate;
    private EditText editTextPwdCurr, editTextPwdNew, editTextPwdConfNew;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwrord);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update password");
        progressBar1= findViewById(R.id.progressBarr);
        editTextPwdCurr= findViewById(R.id.editext_change_psw_curr);
        editTextPwdNew = findViewById(R.id.editext_change_pwd_new);
        editTextPwdConfNew = findViewById(R.id.editext_change_pwd_new_confirm);

        textViewAuthentificated = findViewById(R.id.textView_change_pwd_authentificated);
        buttonChangePwd= findViewById(R.id.button_update_pwd);
        buttonReAuthentificate= findViewById(R.id.button_authenticate_pwd);
        buttonChangePwd.setEnabled(false);
        editTextPwdNew.setEnabled(false);
        editTextPwdConfNew.setEnabled(false);
        authProfile=FirebaseAuth.getInstance();
        firebaseUser= authProfile.getCurrentUser();
        //set old email on text view
       // userPwdCurr= firebaseUser.getEmail();
        if(firebaseUser.equals("")){
            Toast.makeText(changePasswrord.this,"something went wrong! user's detail not available", Toast.LENGTH_LONG).show();
            Intent intent =new Intent(changePasswrord.this, SignIn.class);
            startActivity(intent);
            finish();
        }else {
            reAuthenticateUser(firebaseUser);
        }


    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        buttonReAuthentificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain password for authentication
                userPwdCurr = editTextPwdCurr.getText().toString();
                if(TextUtils.isEmpty(userPwdCurr)){
                    Toast.makeText(changePasswrord.this,"password is needed ", Toast.LENGTH_LONG).show();
                    editTextPwdCurr.setError("please enter your password for authentication");
                    editTextPwdCurr.requestFocus();
                }else{
                    progressBar1.setVisibility(View.VISIBLE);
                    AuthCredential credential= EmailAuthProvider.getCredential(firebaseUser.getEmail(), userPwdCurr);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressBar1.setVisibility(View.GONE);

                                //
                                editTextPwdNew.setEnabled(true);
                                editTextPwdCurr.setEnabled(false);
                                editTextPwdConfNew.setEnabled(true);
                                buttonChangePwd.setEnabled(true);
                               buttonReAuthentificate.setEnabled(false);
                               textViewAuthentificated.setText("you can authenticated, you can change your password now;");
                                Toast.makeText(changePasswrord.this,"password has been verified"+"change your password now", Toast.LENGTH_LONG).show();

                                //change color of update button
                                buttonChangePwd.setBackgroundTintList(ContextCompat.getColorStateList(changePasswrord.this,R.color.green));
                                buttonChangePwd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        changPwd(firebaseUser);


                                    }


                                });
                            }
                            else {
                                progressBar1.setVisibility(View.GONE);
                                try {
                                    throw task.getException();
                                }
                                catch (Exception e){
                                    Toast.makeText(changePasswrord.this, e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }

                        }
                    });
                }
            }
        });
    }
    private void changPwd(FirebaseUser firebaseUser) {
        String userPwdNew= editTextPwdNew.getText().toString();
        String userPwdConfNew= editTextPwdConfNew.getText().toString();
        if(TextUtils.isEmpty(userPwdNew)){
            Toast.makeText(changePasswrord.this,"new password is required", Toast.LENGTH_LONG).show();
            editTextPwdNew.setError("please enter new password");
            editTextPwdNew.requestFocus();
        }
        if(TextUtils.isEmpty(userPwdNew)){
            Toast.makeText(changePasswrord.this,"please confirm your new password", Toast.LENGTH_LONG).show();
            editTextPwdConfNew.setError("please re-enter your new password");
            editTextPwdConfNew.requestFocus();
        }
        else if ( !userPwdNew.matches(userPwdConfNew)) {
            Toast.makeText(changePasswrord.this,"password did not match", Toast.LENGTH_LONG).show();
            editTextPwdNew.setError("please re-enter the same password");
            editTextPwdNew.requestFocus();

        }
        else if ( userPwdNew.matches(userPwdCurr)) {
            Toast.makeText(changePasswrord.this,"New password cannot be the same as the old password", Toast.LENGTH_LONG).show();
            editTextPwdNew.setError("please enter a new password");
            editTextPwdNew.requestFocus();

        }
    else{
        progressBar1.setVisibility(View.VISIBLE);
            firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isComplete()){

                        Toast.makeText(changePasswrord.this,"password has been changed", Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(changePasswrord.this, SignIn.class);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        try {
                            throw task.getException();
                        }
                        catch (Exception e){
                            Toast.makeText(changePasswrord.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }progressBar1.setVisibility(View.GONE);
                }
            });
    }




    }
}