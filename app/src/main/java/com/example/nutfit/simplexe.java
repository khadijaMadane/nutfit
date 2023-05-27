package com.example.nutfit;

import androidx.appcompat.app.AppCompatActivity;
import  com.example.nutfit.nutValues;
import android.os.Bundle;
import android.content.Intent;
import java.io.Serializable;

public class simplexe extends AppCompatActivity {
int numNut,numIng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplexe);
        Intent intent = getIntent();
        int resultIng = intent.getIntExtra("resultIng", 0);
        int resultNut = intent.getIntExtra("resultNut", 0);



    }
}
