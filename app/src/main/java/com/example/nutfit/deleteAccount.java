package com.example.nutfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

public class deleteAccount extends AppCompatActivity {
    private FirebaseAuth authProfite;

    private FirebaseUser firebaseUser;

    private EditText editTextUserPwd ;
    private TextView TextViewAuthenticated;
    private ProgressBar progressBar2;

    private String userPwd;
    private Button buttonReAuthenticate, buttonDeleteUser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        progressBar2 = findViewById(R.id.progressBarrr);
        editTextUserPwd =findViewById(R.id.editext_delete_acc_pwd);
        TextViewAuthenticated =findViewById(R.id.textView_delet_account_authentificated);

        buttonDeleteUser =findViewById(R.id.button_delete_user);

        buttonReAuthenticate= findViewById(R.id.button_authenticate_pwdd);
        //Disabte Delete User Button until User is authenticated buttonDeleteuser.setEnabled(false);

        authProfite = FirebaseAuth.getInstance();

        firebaseUser = authProfite.getCurrentUser();

        if (firebaseUser.equals("")) {

            Toast.makeText( deleteAccount.this,  "Something went wrong!" + "User Detail;s are not available at the moment.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(deleteAccount.this, SignIn.class); startActivity(intent);

            finish();

        } else {

            reAuthenticateUser (firebaseUser);}
    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        buttonReAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain password for authentication
                userPwd = editTextUserPwd.getText().toString();
                if(TextUtils.isEmpty(userPwd)){
                    Toast.makeText(deleteAccount.this,"password is needed ", Toast.LENGTH_LONG).show();
                    editTextUserPwd.setError("please enter your password for authentication");
                    editTextUserPwd.requestFocus();
                }else{
                    progressBar2.setVisibility(View.VISIBLE);
                    AuthCredential credential= EmailAuthProvider.getCredential(firebaseUser.getEmail(), userPwd);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressBar2.setVisibility(View.GONE);

                                //

                                editTextUserPwd.setEnabled(false);

                                buttonReAuthenticate.setEnabled(false);
                                buttonDeleteUser.setEnabled(true);
                                TextViewAuthenticated.setText("you can authenticated, you can change your password now;");
                                Toast.makeText(deleteAccount.this,"password has been verified"+"you can delete now your account,be careful,this action is irreversible", Toast.LENGTH_LONG).show();

                                //change color of update button
                                buttonDeleteUser.setBackgroundTintList(ContextCompat.getColorStateList(deleteAccount.this,R.color.vert));
                                buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        showAleartDialog();


                                    }




                                });
                            }
                            else {
                                progressBar2.setVisibility(View.GONE);
                                try {
                                    throw task.getException();
                                }
                                catch (Exception e){
                                    Toast.makeText(deleteAccount.this, e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }

                        }
                    });
                }
            }
        });
    }
    private void showAleartDialog() {
        //Setup the Alert Builder

        AlertDialog.Builder builder =new AlertDialog.Builder( deleteAccount.this);
        builder.setTitle("delete user and related data");
        builder.setMessage("Do you really want to delete  your profile and related data?");

//Open Email Apps if User clicks/taps Continue button

        builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser(firebaseUser);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent =new Intent(deleteAccount.this, deleteAccount.class);
                startActivity(intent);
                finish();

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.vert));
            }
        });
//Show the AlertDialog
        alertDialog.show();
    }

    private void deleteUser(FirebaseUser firebaseUser) {
        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    authProfite.signOut();

                    Toast.makeText(deleteAccount.this, "User has been deleted!",

                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(deleteAccount.this, SignIn.class);

                    startActivity(intent);

                    finish();

                } else {

                    try {

                        throw task.getException();

                    } catch (Exception e) {

                        Toast.makeText(deleteAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar2.setVisibility(View.GONE);

                    }
                }
            }
        });

    }}