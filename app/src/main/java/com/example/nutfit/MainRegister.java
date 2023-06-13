package com.example.nutfit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MainRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        // Retrieve the matrixList from the intent
        List<Map<String, Object>> matrixList = (List<Map<String, Object>>) getIntent().getSerializableExtra("matrixList");

// Assuming you have a TextView with the ID "matrixTextView" in your activity's layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView matrixTextView = findViewById(R.id.matrixTextView);

// Check if the matrixList is not null and proceed to display the data
        if (matrixList != null) {
            StringBuilder matrixText = new StringBuilder();
            for (Map<String, Object> row : matrixList) {
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    String columnValue = String.valueOf(entry.getValue());
                    matrixText.append(columnValue).append("\t");
                }
                matrixText.append("\n");
            }

            matrixTextView.setText(matrixText.toString());
        } else {
            matrixTextView.setText("No matrix data available.");
        }

    }
}