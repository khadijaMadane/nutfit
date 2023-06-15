package com.example.nutfit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
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
        // Retrieve the matrixList from the intent
        // Retrieve the matrixList from the intent
        // Retrieve the matrixList from the intent
        String matrixListJson = getIntent().getStringExtra("matrixList");
        Log.d("afficheRepas", "matrixListJson: " + matrixListJson);

        if (matrixListJson != null) {
            matrixList = new Gson().fromJson(matrixListJson, new TypeToken<List<List<Object>>>() {
            }.getType());
            Log.d("afficheRepas", "matrixList: " + matrixList);

            // Get the number of columns in the matrixList
            int numColumns = matrixList.get(0).size();

            // Iterate through the matrixList and create rows in the TableLayout
            for (List<Object> rowList : matrixList) {
                TableRow tableRow = new TableRow(this);

                // Iterate through each value in the rowList
                for (Object value : rowList) {
                    // Create a TextView for each value
                    TextView textView = new TextView(this);
                    if (value != null) {
                        textView.setText(value.toString());
                    } else {
                        textView.setText("N/A"); // Set a default text if the value is null
                    }
                    textView.setPadding(8, 8, 8, 8);
                    textView.setTextColor(Color.WHITE);
                    tableRow.addView(textView);
                }

                // Add empty cells if the rowList has fewer columns than numColumns
                int numEmptyCells = numColumns - rowList.size();
                for (int i = 0; i < numEmptyCells; i++) {
                    TextView textView = new TextView(this);
                    textView.setText("N/A"); // Set a default text for the empty cell
                    textView.setPadding(8, 8, 8, 8);
                    textView.setTextColor(Color.WHITE);
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
        // Retrieve quantiteList from the intent
        String quantiteListJson = getIntent().getStringExtra("quantiteList");
        Log.d("affichequantite", "quantiteListJson: " + quantiteListJson);
        List<Quantite> quantiteList = new Gson().fromJson(quantiteListJson, new TypeToken<List<Quantite>>() {}.getType());

// Retrieve priceTotale from the intent
        String priceTotale = getIntent().getStringExtra("priceTotale");

// Get the TextView to display priceTotale
        TextView priceTotaleTextView = findViewById(R.id.priceTotaleTextView);
        priceTotaleTextView.setText(priceTotale);
        // Get the LinearLayout to add TextViews
        LinearLayout quantiteLayout = findViewById(R.id.quantiteLayout);


// Create TextViews for each quantite value and add them to the LinearLayout
        if (quantiteList != null) {
            for (Quantite quantite : quantiteList) {
                String quantiteValue = quantite.getQuantite(); // Obtenez la quantité à partir de l'objet Quantite
                Log.d("affichequantite", "quantiteValue: " + quantiteValue); // Add this line

                TextView quantiteTextView = new TextView(this);
                quantiteTextView.setText(quantiteValue);
                quantiteTextView.setPadding(8, 8, 8, 8);
                quantiteTextView.setTextColor(Color.WHITE); // Set the text color to white

                quantiteLayout.addView(quantiteTextView);

            }
        }
     else {
        Log.d("affichequantite", "quantiteList is null");
        // Handle the case when quantiteList is null
    }

    }}