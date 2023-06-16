package com.example.nutfit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Data_activity extends AppCompatActivity {

    TextView txt_title,txt_description;
    ImageView my_image;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        txt_title=findViewById(R.id.id_txt_title);
        txt_description=findViewById(R.id.id_desc);
        my_image=findViewById(R.id.id_img);

        txt_title.setText(getIntent().getExtras().getString("Title"));
        txt_description.setText(getIntent().getExtras().getString("Des"));

        int my_int_image=getIntent().getIntExtra("Image",0);
        my_image.setImageResource(my_int_image);
    }
}