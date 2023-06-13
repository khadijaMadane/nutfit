package com.example.nutfit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class afficheRepas extends AppCompatActivity {
    private List<List<Object>> matrixList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_repas);

        TableLayout matrixTableLayout = findViewById(R.id.matrixTableLayout);

        // Retrieve the matrixList from the intent
        String matrixListJson = getIntent().getStringExtra("matrixList");
        Log.d("afficheRepas", "matrixListJson: " + matrixListJson);

        if (matrixListJson != null) {
            matrixList = new Gson().fromJson(matrixListJson, new TypeToken<List<List<Object>>>() {}.getType());
            Log.d("afficheRepas", "matrixList: " + matrixList);

            // Iterate through the matrixList and create rows in the TableLayout
            for (List<Object> rowList : matrixList) {
                TableRow tableRow = new TableRow(this);

                // Iterate through each value in the row
                for (Object value : rowList) {
                    // Create a TextView for each value
                    TextView textView = new TextView(this);
                    if (value != null) {
                        textView.setText(value.toString());
                    } else {
                        textView.setText("N/A"); // Set a default text if the value is null
                    }
                    textView.setPadding(8, 8, 8, 8);
                    tableRow.addView(textView);
                }

                // Add the TableRow to the TableLayout
                matrixTableLayout.addView(tableRow);
            }
        } else {
            // Handle the case when the matrixListJson is null
            Toast.makeText(this, "Matrix data is not available", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
